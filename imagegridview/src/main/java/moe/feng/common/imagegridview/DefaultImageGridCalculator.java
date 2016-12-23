package moe.feng.common.imagegridview;

import android.content.Context;

import moe.feng.common.imagegridview.util.ScreenUtils;

public class DefaultImageGridCalculator extends ImageGridCalculator {

	public DefaultImageGridCalculator(Context context) {
		super(context);
	}

	@Override
	public int calculateSpan(int position) {
		switch (getCount()) {
			case 3:
				return position == 0 ? 2 : 1;
			case 5:
			case 8:
				return position <= 1 ? 3 : 2;
			case 7:
				return position <= 2 ? 4 : 3;
			case 1:
			case 2:
			case 4:
			case 6:
			case 9:
			default:
				return 1;
		}
	}

	@Override
	public int calculateSpanCount() {
		switch (getCount()) {
			case 1:
			case 2:
			case 3:
			case 4:
				return Math.min(2, getCount());
			case 5:
			case 8:
				return 6;
			case 7:
				return 12;
			case 6:
			case 9:
			default: return 3;
		}
	}

	@Override
	public int calculateHeight(int position) {
		return ScreenUtils.getScreenWidth(getContext()) / getSpanCountInLineByPosition(position);
	}

	private int getSpanCountInLineByPosition(int position) {
		switch (getCount()) {
			case 1:
			case 2:
			case 4:
				return Math.min(2, getCount());
			case 3:
				return position == 0 ? 1 : 2;
			case 5:
			case 8:
				return position <= 1 ? 2 : 3;
			case 7:
				return position <= 2 ? 3 : 4;
			case 6:
			case 9:
			default: return 3;
		}
	}

}
