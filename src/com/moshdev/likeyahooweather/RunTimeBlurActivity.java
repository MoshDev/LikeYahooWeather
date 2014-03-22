package com.moshdev.likeyahooweather;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.moshdev.likeyahooweather.adapter.BlurListAdapter;
import com.moshdev.likeyahooweather.utils.ImageUtils;
import com.moshdev.likeyahooweather.utils.ImageUtils.BlurEffectListener;

public class RunTimeBlurActivity extends ActionBarActivity {

	private ImageView blurredImageView;
	private ImageView nonBlurImageView;

	private ListView listView;
	private ScrollView scrollView;
	private View titleView;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_runtime_blur);

		initActionBar();

		intImages();

		initList();

		listenToScroll();

		titleView = findViewById(R.id.title_bg);

	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		actionBar.setDisplayShowTitleEnabled(false);
	};

	private void intImages() {

		int screenHeight = ImageUtils.getScreenHeight(this)
				+ MainActivity.BACKGROUND_SHIFT;

		blurredImageView = (ImageView) findViewById(R.id.blured_image);
		nonBlurImageView = (ImageView) findViewById(R.id.orginal_image);

		setViewHeight(nonBlurImageView, screenHeight);
		setViewHeight(blurredImageView, screenHeight);

		loadBlurredImage();

	}

	private void initList() {
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new BlurListAdapter(this));

		scrollView = (ScrollView) findViewById(R.id.bgScrollView);
	}

	private void loadBlurredImage() {
		ImageUtils.getBlurredImage(this, R.drawable.bg1,
				ImageUtils.getImageName(R.drawable.bg1), 20,
				new BlurEffectListener() {

					@Override
					public void onDone(Bitmap bitmap) {
						blurredImageView.setImageBitmap(bitmap);
					}
				});
	}

	public void setViewHeight(View v, int height) {
		LayoutParams params = v.getLayoutParams();
		params.height = height;
		v.setLayoutParams(params);
	}

	@SuppressLint("NewApi")
	public void setTitleAlpha(float val) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			titleView.setAlpha(val);
		} else {
			int m = (int) (val * 255);
			titleView.setAlpha(m);
		}
	}

	@SuppressLint("NewApi")
	private void listenToScroll() {

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@SuppressWarnings("deprecation")
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				View v = view.findViewById(R.id.id0);
				if (v != null) {
					int scrollY = -v.getTop();

					if (scrollY < 512 && scrollY >= 0) {

						float val = 0;
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							val = scrollY * (1f / 512f);
							blurredImageView.setAlpha(val);
						} else {
							val = scrollY / 2;
							blurredImageView.setAlpha((int) val);

						}
						setTitleAlpha(val);
					}
					if (scrollY < MainActivity.BACKGROUND_SHIFT * 2
							&& scrollY >= 0) {
						scrollView.scrollTo(0, scrollY / 3);
					}
				}

			}
		});
	}
}
