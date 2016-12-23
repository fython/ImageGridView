package moe.feng.common.imagegridview.loader.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.InputStream;

import moe.feng.common.imagegridview.ImageLoader;
import moe.feng.common.imagegridview.model.ImageSource;
import moe.feng.common.imagegridview.util.NetworkUtils;

public class GlideImageLoader extends ImageLoader {

	@Override
	public void onLoad(final ImageView imageView, final ImageSource source,
	                   @DrawableRes final int placeHolderResId) {
		final Context context = imageView.getContext();
		if (NetworkUtils.isConnected(context)) {
			if (shouldUseLessTrafficData(context)) {
				DrawableRequestBuilder request = Glide.with(context)
						.using(new NetworkDisablingLoader<String>())
						.load(source.getHighQualityUrl());


				if (placeHolderResId != -1) {
					request.placeholder(placeHolderResId);
				}

				request.listener(new RequestListener() {
					@Override
					public boolean onException(Exception e, Object model, Target target,
					                           boolean isFirstResource) {
						 DrawableRequestBuilder request1
								 = Glide.with(context).load(source.getLowQualityUrl());
						if (placeHolderResId != -1) {
							request1.placeholder(placeHolderResId);
						}
						request1.into(imageView);
						return true;
					}

					@Override
					public boolean onResourceReady(Object resource, Object model,
					                               Target target, boolean isFromMemoryCache,
					                               boolean isFirstResource) {
						return false;
					}
				});
				request.into(imageView);
			} else {
				DrawableRequestBuilder request = Glide.with(context).load(source.getHighQualityUrl());

				if (placeHolderResId != - 1) {
					request.placeholder(placeHolderResId);
				}

				request.listener(new RequestListener() {
					@Override
					public boolean onException(Exception e, Object model, Target target,
					                           boolean isFirstResource) {
						DrawableRequestBuilder request1 = Glide.with(context)
								.load(source.getLowQualityUrl());
						if (placeHolderResId != -1) {
							request1.placeholder(placeHolderResId);
						}
						request1.into(imageView);
						return true;
					}

					@Override
					public boolean onResourceReady(Object resource, Object model,
					                               Target target, boolean isFromMemoryCache,
					                               boolean isFirstResource) {
						return false;
					}
				});
				request.into(imageView);
			}
		} else {
			DrawableRequestBuilder request = Glide.with(context)
					.using(new NetworkDisablingLoader<String>())
					.load(source.getHighQualityUrl());

			if (placeHolderResId != -1) {
				request.placeholder(placeHolderResId);
			}

			request.listener(new RequestListener() {
				@Override
				public boolean onException(Exception e, Object model, Target target,
				                           boolean isFirstResource) {
					DrawableRequestBuilder request1 = Glide.with(context)
							.using(new NetworkDisablingLoader<String>())
							.load(source.getLowQualityUrl());
					if (placeHolderResId != -1) {
						request1.placeholder(placeHolderResId);
					}
					request1.into(imageView);
					return true;
				}

				@Override
				public boolean onResourceReady(Object resource, Object model,
				                               Target target, boolean isFromMemoryCache,
				                               boolean isFirstResource) {
					return false;
				}
			});
			request.into(imageView);
		}
	}

	class NetworkDisablingLoader<T> implements StreamModelLoader<T> {
		@Override public DataFetcher<InputStream> getResourceFetcher(final T model, int i, int i1) {
			return new DataFetcher<InputStream>() {
				@Override public InputStream loadData(Priority priority) throws Exception {
					throw new IOException();
				}
				@Override public void cleanup() { }
				@Override public String getId() { return model.toString(); }
				@Override public void cancel() { }
			};
		}
	}

}
