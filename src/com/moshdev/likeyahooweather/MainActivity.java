package com.moshdev.likeyahooweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	public static final int BACKGROUND_SHIFT = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(this);
		String[] items = new String[] { "RunTime Blur", "Pre-Blur Image" };
		listView.setAdapter(new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, items));
		setContentView(listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					startActivity(new Intent(getApplicationContext(),
							RunTimeBlurActivity.class));
				} else if (position == 1) {
					startActivity(new Intent(getApplicationContext(),
							PreBlurActivity.class));
				}

			}
		});
	}

}
