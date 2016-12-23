package moe.feng.common.imagegridview.loader.fresco;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import moe.feng.common.imagegridview.ImageLoader;
import moe.feng.common.imagegridview.model.ImageSource;
import moe.feng.common.imagegridview.util.NetworkUtils;

public class FrescoImageLoader extends ImageLoader {

	@Override
	public void onLoad(ImageView imageView, final ImageSource source,
	                   @DrawableRes int placeHolderResId) {
		final SimpleDraweeView draweeView = (SimpleDraweeView) imageView;
		final Context context = imageView.getContext();
		if (placeHolderResId != 0) {
			draweeView.getHierarchy().setPlaceholderImage(placeHolderResId);
		}
		if (NetworkUtils.isConnected(context)) {
			if (shouldUseLessTrafficData(context)) {
				draweeView.setImageURI(source.getHighQualityUrl());
			} else {
				draweeView.setImageURI(source.getLowQualityUrl());
			}
		} else {
			ControllerListener<ImageInfo> listener = new ControllerListener<ImageInfo>() {
				@Override
				public void onSubmit(String id, Object callerContext) {

				}

				@Override
				public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

				}

				@Override
				public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

				}

				@Override
				public void onIntermediateImageFailed(String id, Throwable throwable) {

				}

				@Override
				public void onFailure(String id, Throwable throwable) {
					draweeView.setImageURI(source.getLowQualityUrl());
				}

				@Override
				public void onRelease(String id) {

				}
			};

			DraweeController controller = Fresco.newDraweeControllerBuilder()
					.setControllerListener(listener)
					.setUri(source.getHighQualityUrl())
					.build();
			draweeView.setController(controller);
		}
	}

}
