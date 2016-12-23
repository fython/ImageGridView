package moe.feng.common.imagegridview.loader.picasso;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import moe.feng.common.imagegridview.ImageLoader;
import moe.feng.common.imagegridview.model.ImageSource;
import moe.feng.common.imagegridview.util.NetworkUtils;

public class PicassoImageLoader extends ImageLoader {

	@Override
	public void onLoad(final ImageView imageView, final ImageSource source,
	                   @DrawableRes final int placeHolderResId) {
		final Context context = imageView.getContext();
		if (NetworkUtils.isConnected(context)) {
			if (shouldUseLessTrafficData(context)) {
				RequestCreator request = Picasso.with(context).load(source.getHighQualityUrl())
						.networkPolicy(NetworkPolicy.OFFLINE);

				if (placeHolderResId != -1) {
					request.placeholder(placeHolderResId);
				}

				request.into(imageView, new Callback() {
					@Override public void onSuccess() {
					}
					@Override
					public void onError() {
						RequestCreator request1 = Picasso.with(context)
								.load(source.getLowQualityUrl());
						if (placeHolderResId != -1) {
							request1.placeholder(placeHolderResId);
						}
						request1.into(imageView);
					}
				});
			} else {
				RequestCreator request = Picasso.with(context).load(source.getHighQualityUrl());

				if (placeHolderResId != - 1) {
					request.placeholder(placeHolderResId);
				}

				request.into(imageView, new Callback() {
					@Override public void onSuccess() {
					}
					@Override
					public void onError() {
						RequestCreator request1 = Picasso.with(context)
								.load(source.getLowQualityUrl());
						if (placeHolderResId != -1) {
							request1.placeholder(placeHolderResId);
						}
						request1.into(imageView);
					}
				});
			}
		} else {
			RequestCreator request = Picasso.with(context).load(source.getHighQualityUrl())
					.networkPolicy(NetworkPolicy.OFFLINE);

			if (placeHolderResId != -1) {
				request.placeholder(placeHolderResId);
			}

			request.into(imageView, new Callback() {
				@Override public void onSuccess() {
				}

				@Override
				public void onError() {
					RequestCreator request1 = Picasso.with(context).load(source.getLowQualityUrl())
							.networkPolicy(NetworkPolicy.OFFLINE);
					if (placeHolderResId != -1) {
						request1.placeholder(placeHolderResId);
					}
					request1.into(imageView);
				}
			});
		}
	}

}
