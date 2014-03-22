package com.moshdev.likeyahooweather.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moshdev.likeyahooweather.R;
import com.moshdev.likeyahooweather.utils.ImageUtils;

public class BlurListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private int screenHeight;

	public BlurListAdapter(Activity activity) {
		layoutInflater = LayoutInflater.from(activity);
		screenHeight = ImageUtils.getScreenHeight(activity);
	}

	@Override
	public int getCount() {
		return 50;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (getItemViewType(position)) {
		case 0:
			return getFirstView(position, convertView, parent);
		case 1:
			return getSecondView(position, convertView, parent);
		default:
			return null;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		}
		return 1;
	}

	private View getFirstView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.layout_first_page,
					parent, false);
			ViewGroup.LayoutParams params = convertView.getLayoutParams();
			params.height = screenHeight;
			convertView.setLayoutParams(params);
			convertView.setId(R.id.id0);
		}

		return convertView;
	}

	private View getSecondView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					android.R.layout.simple_list_item_1, parent, false);
		}

		TextView txt = (TextView) convertView;
		txt.setText("Position :" + position);

		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

}
