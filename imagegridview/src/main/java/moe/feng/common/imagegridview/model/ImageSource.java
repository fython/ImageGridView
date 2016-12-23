package moe.feng.common.imagegridview.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageSource implements Parcelable {

	private String mHighQuality, mLowQuality;
	private Bitmap mBitmap;

	public ImageSource(Bitmap bitmap) {
		this.mBitmap = bitmap;
	}

	public ImageSource(String url) {
		this.mHighQuality = this.mLowQuality = url;
	}

	public ImageSource(String highQualityUrl, String lowQualityUrl) {
		this.mHighQuality = highQualityUrl;
		this.mLowQuality = lowQualityUrl;
	}

	protected ImageSource(Parcel in) {
		mHighQuality = in.readString();
		mLowQuality = in.readString();
		mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
	}

	public String getHighQualityUrl() {
		return mHighQuality;
	}

	public String getLowQualityUrl() {
		return mLowQuality == null ? mHighQuality : mLowQuality;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setUrl(String url) {
		this.mHighQuality = this.mLowQuality = url;
	}

	public void setUrl(String highQualityUrl, String lowQualityUrl) {
		this.mHighQuality = highQualityUrl;
		this.mLowQuality = lowQualityUrl;
	}

	public static final Creator<ImageSource> CREATOR = new Creator<ImageSource>() {
		@Override
		public ImageSource createFromParcel(Parcel in) {
			return new ImageSource(in);
		}

		@Override
		public ImageSource[] newArray(int size) {
			return new ImageSource[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mHighQuality);
		dest.writeString(mLowQuality);
		dest.writeParcelable(mBitmap, flags);
	}

}
