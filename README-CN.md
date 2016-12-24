
## ImageGridView

> Help you to create a flexible image grid easily.
>
> 帮助你轻松的创建一个灵活的图片 Grid 布局

<a href="./screenshots/0.jpg"><img src="./screenshots/0.jpg" width="40%"/></a>
<a href="./screenshots/1.jpg"><img src="./screenshots/1.jpg" width="40%"/></a>

### 如何使用？

首先，引入核心库 (`:imagegridview` 模块) 到你的项目中。

在依赖中加入:

```
dependencies {
    compile 'moe.feng.common.imagegridview:imagegridview:1.0'
}
```

它只包含 ImageGridView, DefaultImageViewCreator 和 DefaultImageGridCalculator。

所以你需要引入一个图像加载库，或者自己写一个。

如果你想使用 Picasso，请引入 PicassoImageLoader。

```
dependencies {
    compile 'moe.feng.common.imagegridview:picassoimageloader:1.0'
}
```

除此以外，我还写了 GlideImageLoader, FrescoImageLoader 提供给 Glide/Fresco 用户.

### 基本

在 XML 代码（例子）:

```
<moe.feng.common.imagegridview.ImageGridView
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:id="@+id/image_grid_view"
			app:imageLoaderClass="moe.feng.common.imagegridview.loader.picasso.PicassoImageLoader"/>
```

用这一句来加载图片:

`mImageGridView.setImages(List<ImageSource> list);`

如果你想更换 ImageViewCreator/ImageLoader，不要忘了在设定后调用 `reload()`。


#### 使用 Picasso / Glide

只需要设定 `PicassoImageLoader` / `GlideImageLoader` 为 ImageLoader。

#### 使用 FrescoImageLoader

设定 `FrescoImageLoader` 为 ImageLoader，并设定 `FrescoImageViewCreator` 为 ImageViewCreator （为了让 DraweeView 代替 ImageView）。

#### 定制你自己的布局排列

学习 [GridLayoutManager](https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html) 和 [GridLayoutManager.SpanSizeLookup](https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.SpanSizeLookup.html) 的文档。继承 `ImageGridCalculator` 重写一个类进行计算。

### 联系我

Email: fython#163.com

通过支付宝捐赠支持: 316643843#qq.com

通过 Paypal 捐赠（不推荐）: https://paypal.me/fython

### 协议

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
