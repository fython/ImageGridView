package moe.feng.common.imagegridview.util;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public final class ScreenUtils {

	public static int getTrueScreenHeight(Context context) {
		int dpi = 0;
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= 17) {
			display.getRealMetrics(dm);
			dpi = dm.heightPixels;
		} else {
			try {
				Class c = Class.forName("android.view.Display");
				Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, dm);
				dpi = dm.heightPixels;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dpi;
	}

	public static int getTrueScreenWidth(Context context) {
		int dpi = 0;
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= 17) {
			display.getRealMetrics(dm);
			dpi = dm.widthPixels;
		} else {
			try {
				Class c = Class.forName("android.view.Display");
				Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, dm);
				dpi = dm.widthPixels;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dpi;
	}

	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.heightPixels;
	}

	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getNavigationBarHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);

		return getTrueScreenHeight(context) - dm.heightPixels;
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getFontHeight(Context context, float fontSize) {
		// Convert Dp To Px
		float px = context.getResources().getDisplayMetrics().density * fontSize + 0.5f;

		// Use Paint to get font height
		Paint p = new Paint();
		p.setTextSize(px);
		Paint.FontMetrics fm = p.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

}
