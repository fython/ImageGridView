package moe.feng.common.imagegridview;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import moe.feng.common.imagegridview.model.ImageSource;

class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageHolder> {

	private List<ImageSource> mSources;
	private @DrawableRes int mPlaceholderResId = 0;

	private boolean mShouldIgnoreDataSaver = false;

	private ImageViewCreator mImageViewCreator = new DefaultImageViewCreator();
	private ImageLoader mImageLoader;
	private ImageGridView.OnItemClickListener mOnItemClickListener;
	private ItemHeightCalculator mItemHeightCalculator;

	private int viewType = 0;

	void setImages(List<ImageSource> images) {
		this.mSources = images;
	}

	void setImageViewCreator(ImageViewCreator imageViewCreator) {
		this.mImageViewCreator =
				imageViewCreator == null ? new DefaultImageViewCreator() : imageViewCreator;
		viewType++;
	}

	void setImageLoader(ImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
		this.mImageLoader.shouldIgnoreDataSaver = mShouldIgnoreDataSaver;
	}

	void setOnItemClickListener(ImageGridView.OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	void setPlaceHolderResource(@DrawableRes int placeHolderResId) {
		this.mPlaceholderResId = placeHolderResId;
	}

	void setShouldIgnoreDataSaver(boolean shouldIgnoreDataSaver) {
		this.mShouldIgnoreDataSaver = shouldIgnoreDataSaver;
		if (mImageLoader != null) {
			mImageLoader.shouldIgnoreDataSaver = shouldIgnoreDataSaver;
		}
	}

	void setItemHeightCalculator(ItemHeightCalculator itemHeightCalculator) {
		this.mItemHeightCalculator = itemHeightCalculator;
	}

	ImageLoader getImageLoader() {
		return mImageLoader;
	}

	ImageViewCreator getImageViewCreator() {
		return mImageViewCreator;
	}

	boolean getShouldIgnoreDataSaver() {
		return mShouldIgnoreDataSaver;
	}

	@Override
	public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ImageHolder(mImageViewCreator.createView(parent.getContext(), parent));
	}

	@Override
	public void onBindViewHolder(ImageHolder holder, int position) {
		if (mItemHeightCalculator != null) {
			holder.imageView.getLayoutParams().height =
					mItemHeightCalculator.calculateHeight(position);
		}

		if (mSources.get(position).getBitmap() != null) {
			holder.imageView.setImageBitmap(mSources.get(position).getBitmap());
		} else {
			if (mImageLoader == null) {
				throw new IllegalStateException("Did you forget to set a ImageLoader?");
			}
			mImageLoader.onLoad(holder.imageView, mSources.get(position), mPlaceholderResId);
		}
	}

	@Override
	public int getItemCount() {
		return mSources == null ? 0 : mSources.size();
	}

	@Override
	public int getItemViewType(int position) {
		return viewType;
	}

	class ImageHolder extends RecyclerView.ViewHolder {

		ImageView imageView;

		ImageHolder(View imageView) {
			super(imageView);
			this.imageView = (ImageView) imageView;
			this.imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClick(
								getAdapterPosition(), ImageHolder.this.imageView);
					}
				}
			});
		}

	}

	interface ItemHeightCalculator {

		int calculateHeight(int position);

	}

}
