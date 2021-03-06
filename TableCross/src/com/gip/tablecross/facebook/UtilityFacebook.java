package com.gip.tablecross.facebook;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.provider.MediaStore;

import com.gip.tablecross.util.Logger;

public class UtilityFacebook extends Application {
	public static AsyncFacebookRunner mAsyncRunner;
	public static JSONObject mFriendsList;
	public static AndroidHttpClient httpclient = null;
	public static Hashtable<String, String> currentPermissions = new Hashtable<String, String>();

	private static int MAX_IMAGE_DIMENSION = 720;
	private static final String TAG = "UtilityFacebook";
	public static Bitmap srcBitmap;

	public static Bitmap getBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
			bis.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return bm;
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	public static byte[] data;
	public static byte[] bMapArray;

	public static byte[] scaleImage(Context context, Uri photoUri) throws IOException {

		Logger.e(TAG, "uri to upload  : " + photoUri);

		// URI get from customize camera

		if (photoUri.toString().contains(".")) {
			Logger.e(TAG, "Get Image From customize Gallery");
			String path = photoUri.getPath();

			Logger.i("check file exit 1 ", "" + new File(path).exists());
			InputStream is = context.getContentResolver().openInputStream(photoUri);
			BitmapFactory.Options dbo = new BitmapFactory.Options();
			dbo.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, dbo);
			is.close();
			data = null;

			try {
				FileInputStream fis = new FileInputStream(path);
				Bitmap bi = BitmapFactory.decodeStream(fis);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bi.compress(Bitmap.CompressFormat.PNG, 80, baos);
				data = baos.toByteArray();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Logger.d("onCreate", "debug error  e = " + e.toString());
			}
			Logger.e("data.length", " " + data.length);
			return data;

		}
		// URI get from default gallery
		else {
			Logger.e(TAG, "Get Image From default Gallery");
			InputStream is = context.getContentResolver().openInputStream(photoUri);
			BitmapFactory.Options dbo = new BitmapFactory.Options();
			dbo.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, dbo);
			is.close();
			int rotatedWidth, rotatedHeight;
			int orientation = getOrientation(context, photoUri);
			if (orientation == 90 || orientation == 270) {
				rotatedWidth = dbo.outHeight;
				rotatedHeight = dbo.outWidth;
			} else {

				rotatedWidth = dbo.outWidth;
				rotatedHeight = dbo.outHeight;
			}

			// Bitmap srcBitmap;
			is = context.getContentResolver().openInputStream(photoUri);
			if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
				float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
				float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
				float maxRatio = Math.max(widthRatio, heightRatio);

				// Create the bitmap from file
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = (int) maxRatio;
				srcBitmap = BitmapFactory.decodeStream(is, null, options);
			} else {
				srcBitmap = BitmapFactory.decodeStream(is);
			}
			is.close();

			/*
			 * if the orientation is not 0 (or -1, which means we don't know),
			 * we have to do a rotation.
			 */

			if (orientation > 0) {
				Matrix matrix = new Matrix();
				matrix.postRotate(orientation);

				srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix,
						true);
			}

			String type = context.getContentResolver().getType(photoUri);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (type.equals("image/png")) {
				srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
				srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			}
			bMapArray = baos.toByteArray();
			baos.close();
			return bMapArray;
		}
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = null;
		cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor != null && cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		Logger.e("aaaaaaaaaaaaaaaaaaaaaaa", "" + cursor.getInt(0));
		return cursor.getInt(0);
	}
	// scan source media and image in sdcard
	// private void scanSdCardPhoto(Uri uri) {
	// String pathName = uri.getPath();
	// MyMediaConnectorClientS client = new MyMediaConnectorClientS(pathName);
	// MediaScannerConnection scanner = new MediaScannerConnection(
	// UtilityFacebook.this, client);
	// client.setScanner(scanner);
	// scanner.connect();
	// }
}
