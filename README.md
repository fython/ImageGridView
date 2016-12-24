
## ImageGridView

> Help you to create a flexible image grid easily.
>
> 帮助你轻松的创建一个灵活的图片 Grid 布局

[中文说明](./README-CN.md)（作者英文比较渣，建议国人直接阅读中文版）

<a href="./screenshots/0.jpg"><img src="./screenshots/0.jpg" width="40%"/></a>
<a href="./screenshots/1.jpg"><img src="./screenshots/1.jpg" width="40%"/></a>

### How to use?

First, introduce the core library (`:imagegridview` Module) to your project.

Add the dependency:

```
dependencies {
    compile 'moe.feng.common.imagegridview:imagegridview:1.0'
}
```

It includes only ImageGridView, DefaultImageViewCreator and DefaultImageGridCalculator.

So you need introduce a image loader library or write by yourself.

If you want to use Picasso, you can choose PicassoImageLoader.

```
dependencies {
    compile 'moe.feng.common.imagegridview:picassoimageloader:1.0'
}
```

Besides, I also wrote GlideImageLoader, FrescoImageLoader for Glide/Fresco users.

### Basic

In XML (For example) :

```
<moe.feng.common.imagegridview.ImageGridView
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:id="@+id/image_grid_view"
			app:imageLoaderClass="moe.feng.common.imagegridview.loader.picasso.PicassoImageLoader"/>
```

Load images in this way:

`mImageGridView.setImages(List<ImageSource> list);`

If you want to change a ImageViewCreator/ImageLoader, don't forget call `reload()` after setting up.


#### Use Picasso / Glide

Just set a `PicassoImageLoader` / `GlideImageLoader`.

#### Use FrescoImageLoader

Set `FrescoImageLoader` and `FrescoImageViewCreator` (Use DraweeView instead of ImageView).

#### Customize your own grid layout

Learn [GridLayoutManager](https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html) and [GridLayoutManager.SpanSizeLookup](https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.SpanSizeLookup.html). Then rewrite a class extends `ImageGridCalculator`.

### Contact me

Email: fython#163.com

通过支付宝捐赠支持 (Recommended): 316643843#qq.com

Donate by Paypal: https://paypal.me/fython

### License

```
MIT License

Copyright (c) 2016 Fung Go (fython)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
