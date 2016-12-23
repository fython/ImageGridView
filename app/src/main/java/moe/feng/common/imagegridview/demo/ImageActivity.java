package moe.feng.common.imagegridview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import moe.feng.common.imagegridview.loader.fresco.FrescoImageLoader;
import moe.feng.common.imagegridview.loader.glide.GlideImageLoader;
import moe.feng.common.imagegridview.loader.picasso.PicassoImageLoader;
import moe.feng.common.imagegridview.model.ImageSource;

public class ImageActivity extends AppCompatActivity {

	private ImageView mImageView;
	private SimpleDraweeView mDraweeView;

	private static final String TRANSITION_NAME_IMAGE = "image";

	private static final String EXTRA_SRC = "src", EXTRA_LOADER_TYPE = "loader";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mImageView = (ImageView) findViewById(R.id.image_view);
		mDraweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);

		if (getIntent().getIntExtra(EXTRA_LOADER_TYPE, 0) != 2) {
			ViewCompat.setTransitionName(mImageView, TRANSITION_NAME_IMAGE);
		}

		ImageSource imageSource = getIntent().getParcelableExtra(EXTRA_SRC);

		switch (getIntent().getIntExtra(EXTRA_LOADER_TYPE, 0)) {
			case 0:
				new PicassoImageLoader().onLoad(mImageView, imageSource, R.color.placeHolderColor);
				break;
			case 1:
				new GlideImageLoader().onLoad(mImageView, imageSource, R.color.placeHolderColor);
				break;
			case 2:
				new FrescoImageLoader().onLoad(mDraweeView, imageSource, R.color.placeHolderColor);
				break;
		}
	}

	public static void launch(AppCompatActivity activity, ImageView imageView, int loaderType,
	                          ImageSource imageSource) {
		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				activity, imageView, TRANSITION_NAME_IMAGE);

		Intent intent = new Intent(activity, ImageActivity.class);
		intent.putExtra(EXTRA_SRC, imageSource);
		intent.putExtra(EXTRA_LOADER_TYPE, loaderType);

		if (loaderType != 2) {
			ActivityCompat.startActivity(activity, intent, options.toBundle());
		} else {
			activity.startActivity(intent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(menuItem);
	}

}
