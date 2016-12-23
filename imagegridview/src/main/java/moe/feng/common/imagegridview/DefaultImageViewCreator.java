package moe.feng.common.imagegridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DefaultImageViewCreator extends ImageViewCreator {

	@Override
	public ImageView createView(Context context, ViewGroup parent) {
		return (ImageView) LayoutInflater.from(context)
				.inflate(R.layout.default_image_view, parent, false);
	}

}
