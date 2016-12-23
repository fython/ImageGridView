package moe.feng.common.imagegridview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class ImageViewCreator {

	public abstract ImageView createView(Context context, ViewGroup parent);

}
