package com.moshdev.likeyahooweather.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;

public class ImageUtils {
	private static final String TAG = "ImageUtils";

	/**
	 * Stores an image on the storage
	 * 
	 * @param image
	 *            the image to store.
	 * @param pictureFile
	 *            the file in which it must be stored
	 */
	public static void storeImage(Bitmap image, File pictureFile) {
		if (pictureFile == null) {
			Log.d(TAG, "Error creating media file, check storage permissions: ");
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			image.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d(TAG, "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Error accessing file: " + e.getMessage());
		}
	}

	/**
	 * Get the screen height.
	 * 
	 * @param context
	 * @return the screen height
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static int getScreenHeight(Activity context) {

		Display display = context.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);
			return size.y;
		}
		return display.getHeight();
	}

	/**
	 * Get the screen width.
	 * 
	 * @param context
	 * @return the screen width
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static int getScreenWidth(Activity context) {

		Display display = context.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);
			return size.x;
		}
		return display.getWidth();
	}

	public static void getBlurredImage(final Context context,
			final int bitmapRes, final String nameToSave, final int radius,
			final BlurEffectListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File saveFile = new File(context.getFilesDir(), nameToSave);
				Bitmap blurredBitmap = null;
				if (!saveFile.exists()) {
					try {
						Bitmap orgBitmap = BitmapFactory.decodeResource(
								context.getResources(), bitmapRes);
						blurredBitmap = Blur.fastblur(context, orgBitmap,
								radius);
						saveFile.createNewFile();
						ImageUtils.storeImage(blurredBitmap, saveFile);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					blurredBitmap = BitmapFactory.decodeFile(saveFile
							.getAbsolutePath());
				}
				final Bitmap tempBitmap = blurredBitmap;
				Handler handler = new Handler(Looper.getMainLooper());
				handler.post(new Runnable() {

					@Override
					public void run() {
						listener.onDone(tempBitmap);
					}
				});

			}
		}).start();
	}

	public static interface BlurEffectListener {

		public void onDone(Bitmap bitmap);
	}

	public static String getImageName(int resId) {
		return resId + "_cached.png";
	}
}
