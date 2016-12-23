package moe.feng.common.imagegridview.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.net.ConnectivityManagerCompat;

public final class NetworkUtils {

	public static boolean isConnected(Context context) {
		ConnectivityManager cm;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			cm = context.getSystemService(ConnectivityManager.class);
		} else {
			cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	// A new api for Android 7.0 or newer version
	public static boolean shouldUseLessTrafficData(Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			return false;
		}

		ConnectivityManager cm;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			cm = context.getSystemService(ConnectivityManager.class);
		} else {
			cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		// Checks if the device is on a metered network
		if (cm.isActiveNetworkMetered()) {
			// Checks user’s Data Saver settings.
			switch (ConnectivityManagerCompat.getRestrictBackgroundStatus(cm)) {
				case ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_ENABLED:
					// Background data usage is blocked for this app. Wherever possible,
					// the app should also use less data in the foreground.
					return true;
				case ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_WHITELISTED:
					// The app is whitelisted. Wherever possible,
					// the app should use less data in the foreground and background.
					return false;
				case ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_DISABLED:
					// Data Saver is disabled. Since the device is connected to a
					// metered network, the app should use less data wherever possible.
					return false;
			}
			return false;
		} else {
			// The device is not on a metered network.
			// Use data as required to perform syncs, downloads, and updates.
			return false;
		}
	}

}
