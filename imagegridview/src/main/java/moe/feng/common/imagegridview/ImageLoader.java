package moe.feng.common.imagegridview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import moe.feng.common.imagegridview.model.ImageSource;
import moe.feng.common.imagegridview.util.NetworkUtils;

public abstract class ImageLoader {

	boolean shouldIgnoreDataSaver = false;

	public abstract void onLoad(
		ImageView imageView,
	    ImageSource source,
	    @DrawableRes int placeHolderResId
	);

	protected boolean shouldUseLessTrafficData(Context context) {
		return !shouldIgnoreDataSaver && NetworkUtils.shouldUseLessTrafficData(context);
	}

}