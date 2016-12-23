package moe.feng.common.imagegridview;

import android.content.Context;

public abstract class ImageGridCalculator implements ImageGridAdapter.ItemHeightCalculator {

	private int mCount;

	private Context context;

	public ImageGridCalculator(Context context) {
		this.context = context;
	}

	public void setCount(int count) {
		this.mCount = count;
	}

	public int getCount() {
		return this.mCount;
	}

	public abstract int calculateSpan(int position);

	public abstract int calculateSpanCount();

	public Context getContext() {
		return this.context;
	}

}
