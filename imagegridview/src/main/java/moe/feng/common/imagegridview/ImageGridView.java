package moe.feng.common.imagegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import moe.feng.common.imagegridview.model.ImageSource;

public class ImageGridView extends FrameLayout {

	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;
	private ImageGridAdapter mAdapter;

	private ImageGridCalculator mGridCalculator;

	private List<ImageSource> mImages;

	private int mCurrentSpanCount = DEFAULT_SPAN_COUNT;

	private static final int DEFAULT_SPAN_COUNT = 2;

	public ImageGridView(Context context) {
		this(context, null);
	}

	public ImageGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ImageGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mGridCalculator = new DefaultImageGridCalculator(getContext());
		mRecyclerView = new RecyclerView(getContext());
		mLayoutManager = new GridLayoutManager(getContext(), mCurrentSpanCount);
		mAdapter = new ImageGridAdapter();

		FrameLayout.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(mRecyclerView, layoutParams);

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageGridView,
					defStyleAttr, 0);

			String gridCalculatorName = a.getString(R.styleable.ImageGridView_gridCalculatorClass);
			String imageLoaderName = a.getString(R.styleable.ImageGridView_imageLoaderClass);
			String imageCreatorName = a.getString(R.styleable.ImageGridView_imageCreatorClass);

			createGridCalculator(context, gridCalculatorName);
			createImageLoader(imageLoaderName);
			createImageViewCreator(imageCreatorName);

			boolean shouldIgnoreDataSaver = a.getBoolean(
					R.styleable.ImageGridView_shouldIgnoreDataSaver, false);

			mAdapter.setShouldIgnoreDataSaver(shouldIgnoreDataSaver);

			a.recycle();
		}

		init();
	}

	private void init() {
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				return mGridCalculator.calculateSpan(position);
			}
		});

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

		mAdapter.setItemHeightCalculator(mGridCalculator);
	}

	private void createGridCalculator(Context context, String className) {
		if (className != null) {
			className = className.trim();
			if (!className.isEmpty()) {
				try {
					ClassLoader classLoader;
					if (isInEditMode()) {
						// Stupid layoutlib cannot handle simple class loaders.
						classLoader = this.getClass().getClassLoader();
					} else {
						classLoader = getContext().getClassLoader();
					}
					Class<? extends ImageGridCalculator> imageGridCalculatorClass =
							classLoader.loadClass(className).asSubclass(ImageGridCalculator.class);
					Constructor<? extends ImageGridCalculator> constructor;
					Object[] constructorArgs = null;
					try {
						constructor = imageGridCalculatorClass.getConstructor(Context.class);
						constructorArgs = new Object[]{context};
					} catch (NoSuchMethodException e) {
						try {
							constructor = imageGridCalculatorClass.getConstructor();
						} catch (NoSuchMethodException e1) {
							e1.initCause(e);
							throw new IllegalStateException(
									"Error creating ImageGridCalculator " + className, e1);
						}
					}
					constructor.setAccessible(true);
					mGridCalculator = constructor.newInstance(constructorArgs);
				} catch (ClassNotFoundException e) {
					throw new IllegalStateException(
							"Unable to find ImageGridCalculator " + className, e);
				} catch (InvocationTargetException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageGridCalculator: " + className, e);
				} catch (InstantiationException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageGridCalculator: " + className, e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(
							"Cannot access non-public constructor " + className, e);
				} catch (ClassCastException e) {
					throw new IllegalStateException(
							"Class is not a ImageGridCalculator " + className, e);
				}
			}
		}
	}

	private void createImageLoader(String className) {
		if (className != null) {
			className = className.trim();
			if (!className.isEmpty()) {
				try {
					ClassLoader classLoader;
					if (isInEditMode()) {
						// Stupid layoutlib cannot handle simple class loaders.
						classLoader = this.getClass().getClassLoader();
					} else {
						classLoader = getContext().getClassLoader();
					}
					Class<? extends ImageLoader> imageLoaderClass =
							classLoader.loadClass(className).asSubclass(ImageLoader.class);
					Constructor<? extends ImageLoader> constructor;
					try {
						constructor = imageLoaderClass.getConstructor();
					} catch (NoSuchMethodException e) {
						try {
							constructor = imageLoaderClass.getConstructor();
						} catch (NoSuchMethodException e1) {
							e1.initCause(e);
							throw new IllegalStateException(
									"Error creating ImageLoader " + className, e1);
						}
					}
					constructor.setAccessible(true);
					setImageLoader(constructor.newInstance());
				} catch (ClassNotFoundException e) {
					throw new IllegalStateException(
							"Unable to find ImageLoader " + className, e);
				} catch (InvocationTargetException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageLoader: " + className, e);
				} catch (InstantiationException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageLoader: " + className, e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(
							"Cannot access non-public constructor " + className, e);
				} catch (ClassCastException e) {
					throw new IllegalStateException(
							"Class is not a ImageLoader " + className, e);
				}
			}
		}
	}

	private void createImageViewCreator(String className) {
		if (className != null) {
			className = className.trim();
			if (!className.isEmpty()) {
				try {
					ClassLoader classLoader;
					if (isInEditMode()) {
						// Stupid layoutlib cannot handle simple class loaders.
						classLoader = this.getClass().getClassLoader();
					} else {
						classLoader = getContext().getClassLoader();
					}
					Class<? extends ImageViewCreator> imageViewCreatorClass =
							classLoader.loadClass(className).asSubclass(ImageViewCreator.class);
					Constructor<? extends ImageViewCreator> constructor;
					try {
						constructor = imageViewCreatorClass.getConstructor();
					} catch (NoSuchMethodException e) {
						try {
							constructor = imageViewCreatorClass.getConstructor();
						} catch (NoSuchMethodException e1) {
							e1.initCause(e);
							throw new IllegalStateException(
									"Error creating ImageLoader " + className, e1);
						}
					}
					constructor.setAccessible(true);
					setImageViewCreator(constructor.newInstance());
				} catch (ClassNotFoundException e) {
					throw new IllegalStateException(
							"Unable to find ImageLoader " + className, e);
				} catch (InvocationTargetException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageLoader: " + className, e);
				} catch (InstantiationException e) {
					throw new IllegalStateException(
							"Could not instantiate the ImageLoader: " + className, e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(
							"Cannot access non-public constructor " + className, e);
				} catch (ClassCastException e) {
					throw new IllegalStateException(
							"Class is not a ImageLoader " + className, e);
				}
			}
		}
	}

	public void setImages(List<ImageSource> images){
		this.mImages = images;
		mAdapter.setImages(images);

		mGridCalculator.setCount(getImagesCount());
		mCurrentSpanCount = mGridCalculator.calculateSpanCount();
		mLayoutManager.setSpanCount(mCurrentSpanCount);

		mAdapter.notifyDataSetChanged();
	}

	public List<ImageSource> getImages() {
		return this.mImages;
	}

	public int getImagesCount() {
		return this.mImages == null ? 0 : this.mImages.size();
	}

	public int getCurrentSpanCount() {
		return mCurrentSpanCount;
	}

	public ImageViewCreator getImageViewCreator() {
		return mAdapter == null ? null : mAdapter.getImageViewCreator();
	}

	public ImageLoader getImageLoader() {
		return mAdapter == null ? null : mAdapter.getImageLoader();
	}

	public ImageGridCalculator getImageGridCalculator() {
		return mGridCalculator;
	}

	public boolean getShouldIgnoreDataSaver() {
		return mAdapter != null && mAdapter.getShouldIgnoreDataSaver();
	}

	public void setImageViewCreator(ImageViewCreator creator) {
		mAdapter.setImageViewCreator(creator);
	}

	public void setImageLoader(ImageLoader loader) {
		mAdapter.setImageLoader(loader);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mAdapter.setOnItemClickListener(listener);
	}

	public void setShouldIgnoreDataSaver(boolean shouldIgnoreDataSaver) {
		mAdapter.setShouldIgnoreDataSaver(shouldIgnoreDataSaver);
	}

	public void setPlaceHolderResource(@DrawableRes int placeHolderResId) {
		mAdapter.setPlaceHolderResource(placeHolderResId);
	}

	public void setImageGridCalculator(ImageGridCalculator calculator) {
		this.mGridCalculator = calculator;
		this.mGridCalculator.setCount(getImagesCount());
		mAdapter.setItemHeightCalculator(calculator);

		this.mCurrentSpanCount = mGridCalculator.calculateSpanCount();
		mLayoutManager.setSpanCount(mCurrentSpanCount);
		mLayoutManager.requestLayout();
	}

	public void reload() {
		mAdapter.notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(int position, ImageView imageView);
	}

}
