package com.gip.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gip.tablecross.R;

public class KeywordAdapter extends ArrayAdapter<String> {
	private Activity context;
	private List<String> listNotifications;

	public KeywordAdapter(Activity context, List<String> listNotifications) {
		super(context, R.layout.item_history_search, listNotifications);
		this.context = context;
		this.listNotifications = listNotifications;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_history_search, null);
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.lblKeyword = (TextView) rowView.findViewById(R.id.lblKeyword);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		String item = listNotifications.get(position);
		SpannableString content = new SpannableString(item);
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		holder.lblKeyword.setText(content);

		return rowView;
	}

	static class ViewHolder {
		private TextView lblKeyword;
	}
}