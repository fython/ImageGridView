package moe.feng.common.imagegridview.loader.fresco;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import moe.feng.common.imagegridview.ImageViewCreator;

public class FrescoImageViewCreator extends ImageViewCreator {

	@Override
	public ImageView createView(Context context, ViewGroup parent) {
		SimpleDraweeView view = new SimpleDraweeView(context);
		view.setScaleType(ImageView.ScaleType.CENTER_CROP);

		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		view.setLayoutParams(layoutParams);

		return view;
	}

}
