package moe.feng.common.imagegridview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import moe.feng.common.imagegridview.DefaultImageViewCreator;
import moe.feng.common.imagegridview.ImageGridView;
import moe.feng.common.imagegridview.loader.fresco.FrescoImageLoader;
import moe.feng.common.imagegridview.loader.fresco.FrescoImageViewCreator;
import moe.feng.common.imagegridview.loader.glide.GlideImageLoader;
import moe.feng.common.imagegridview.loader.picasso.PicassoImageLoader;
import moe.feng.common.imagegridview.model.ImageSource;

public class MainActivity extends AppCompatActivity {

	private ImageGridView mImageGridView;

	private static final ImageSource[] PLACEHOLDER = new ImageSource[]{
			new ImageSource("http://ww2.sinaimg.cn/mw690/586f5255gw1fb0t145bf2j20em0kqadw.jpg"),
			new ImageSource("http://ww3.sinaimg.cn/mw690/586f5255gw1fazxwsjwb2j20m80uc10t.jpg"),
			new ImageSource("http://ww2.sinaimg.cn/mw690/586f5255gw1f6b1tgjj6hj20g60rs0wu.jpg"),
			new ImageSource("http://ww3.sinaimg.cn/mw690/586f5255gw1f42xt6sv30j20kt104tjd.jpg"),
			new ImageSource("http://ww2.sinaimg.cn/mw690/586f5255gw1f6jawgxzyjj20hv0tdjyx.jpg"),
			new ImageSource("http://ww3.sinaimg.cn/mw690/586f5255gw1f6gsu1sxubj20hn0p044j.jpg"),
			new ImageSource("http://ww3.sinaimg.cn/mw690/586f5255jw1ew32ea8wwfj21kw27le1m.jpg"),
			new ImageSource("http://ww3.sinaimg.cn/mw690/586f5255jw1eu8114cr5aj20m30uk11b.jpg"),
			new ImageSource("http://ww1.sinaimg.cn/mw690/586f5255jw1etz8kufxqgj20m80v30yu.jpg")
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImageGridView = (ImageGridView) findViewById(R.id.image_grid_view);

		mImageGridView.setOnItemClickListener(new ImageGridView.OnItemClickListener() {
			@Override
			public void onItemClick(int position, ImageView imageView) {
				int loaderType = 0;

				if (mImageGridView.getImageLoader() instanceof PicassoImageLoader) loaderType = 0;
				if (mImageGridView.getImageLoader() instanceof GlideImageLoader) loaderType = 1;
				if (mImageGridView.getImageLoader() instanceof FrescoImageLoader) loaderType = 2;

				ImageActivity.launch(MainActivity.this, imageView, loaderType,
						mImageGridView.getImages().get(position));
			}
		});
		mImageGridView.setShouldIgnoreDataSaver(false);
		mImageGridView.setPlaceHolderResource(R.color.placeHolderColor);

		mImageGridView.setImages(producePlaceholder(5));
	}

	private List<ImageSource> producePlaceholder(int size) {
		return Arrays.asList(PLACEHOLDER).subList(0, Math.min(size, PLACEHOLDER.length));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		int id = menuItem.getItemId();
		switch (id) {
			case R.id.item_placeholder_1:
			case R.id.item_placeholder_2:
			case R.id.item_placeholder_3:
			case R.id.item_placeholder_4:
			case R.id.item_placeholder_5:
			case R.id.item_placeholder_6:
			case R.id.item_placeholder_7:
			case R.id.item_placeholder_8:
			case R.id.item_placeholder_9:
				int number = Integer.parseInt(menuItem.getTitle().toString());
				mImageGridView.setImages(producePlaceholder(number));
				return true;
			case R.id.item_picasso:
				if (mImageGridView.getImageViewCreator() instanceof FrescoImageViewCreator) {
					mImageGridView.setImageViewCreator(new DefaultImageViewCreator());
				}
				mImageGridView.setImageLoader(new PicassoImageLoader());
				mImageGridView.reload();
				return true;
			case R.id.item_glide:
				if (mImageGridView.getImageViewCreator() instanceof FrescoImageViewCreator) {
					mImageGridView.setImageViewCreator(new DefaultImageViewCreator());
				}
				mImageGridView.setImageLoader(new GlideImageLoader());
				mImageGridView.reload();
				return true;
			case R.id.item_fresco:
				mImageGridView.setImageViewCreator(new FrescoImageViewCreator());
				mImageGridView.setImageLoader(new FrescoImageLoader());
				mImageGridView.reload();
				return true;
		}
		return false;
	}

}
