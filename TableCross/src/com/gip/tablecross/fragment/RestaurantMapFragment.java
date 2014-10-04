package com.gip.tablecross.fragment;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.util.Logger;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantMapFragment extends BaseFragment {
//	private View layoutMap;
	private GoogleMap mMap;
	private HashMap<String, Restaurant> markerRestaurantMap;
	private Marker lastOpened = null;
	private Marker currentMaker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
		initUI(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			setMarkOnMap();
		}
	}

	private void initUI(View view) {
//		layoutMap = view.findViewById(R.id.layoutMap);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapRestaurant)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
						+ GlobalValue.locationInfo.lastLat + "," + GlobalValue.locationInfo.lastLong + "&daddr="
						+ marker.getPosition().latitude + "," + marker.getPosition().longitude + "&dirflg=w"));
				startActivity(navigation);
			}
		});

		markerRestaurantMap = new HashMap<String, Restaurant>();
	}

	public void setMarkOnMap() {
		mMap.clear();
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		Marker marker = mMap.addMarker(new MarkerOptions().position(getMainActivity().currentRestaurant.getLatLng())
				.title(getMainActivity().currentRestaurant.getRestaurantName()));
		markerRestaurantMap.put(marker.getId(), getMainActivity().currentRestaurant);
		builder.include(getMainActivity().currentRestaurant.getLatLng());
		setInfoWindowMarker(getMainActivity().currentRestaurant);
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(getMainActivity().currentRestaurant.getLatLng(), 16);
		mMap.animateCamera(cu);
	}

	private void setInfoWindowMarker(final Restaurant restaurant) {
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(Marker marker) {
				// Check if there is an open info window
				if (lastOpened != null) {
					// Close the info window
					lastOpened.hideInfoWindow();

					// Is the marker the same marker that was already open
					if (lastOpened.equals(marker)) {
						// Nullify the lastOpened object
						lastOpened = null;
						// Return so that the info window isn't opened again
						return true;
					}
				}

				// Open the info window for the marker
				marker.showInfoWindow();
				// Re-assign the last opened such that we can close it later
				lastOpened = marker;

				// Event was handled by our code do not launch default
				// behaviour.
				return true;
			}
		});
	}

	private class CustomInfoWindowAdapter implements InfoWindowAdapter {
		private View v;

		@SuppressLint("InflateParams")
		public CustomInfoWindowAdapter() {
			v = getActivity().getLayoutInflater().inflate(R.layout.layout_restaurant_detail, null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			if (currentMaker != null && currentMaker.isInfoWindowShown()) {
				currentMaker.hideInfoWindow();
				currentMaker.showInfoWindow();
			}
			return null;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			currentMaker = marker;
			Logger.d("MapMaker iD ", marker.getId());

			if (getMainActivity().currentRestaurant != null) {
				ImageView imgFood = (ImageView) v.findViewById(R.id.imgFood);
				TextView lblName = (TextView) v.findViewById(R.id.lblRestaurantName);
				TextView lblAddress = (TextView) v.findViewById(R.id.lblRestaurantAddress);
				TextView lblTime = (TextView) v.findViewById(R.id.lblTime);
				TextView lblNumber = (TextView) v.findViewById(R.id.lblNumber);
				TextView lblDescription = (TextView) v.findViewById(R.id.lblDescription);

				new AQuery(getActivity()).id(imgFood).image(getMainActivity().currentRestaurant.getImageUrl(), true,
						true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
				lblName.setText(getMainActivity().currentRestaurant.getRestaurantName());
				lblTime.setText(getMainActivity().currentRestaurant.getPhone());
				lblAddress.setText(getMainActivity().currentRestaurant.getAddress());
				lblNumber.setText(getMainActivity().currentRestaurant.getOrderCount());
				lblDescription.setText(getMainActivity().currentRestaurant.getShortDescription());
			} else {
				Logger.d("Restaurant map", "Restaurant null");
			}
			return v;
		}
	}
}