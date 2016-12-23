package moe.feng.common.imagegridview.demo;

import com.facebook.drawee.backends.pipeline.Fresco;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();

		/** When you choose Fresco as image loading library, you need initialize it. */
		Fresco.initialize(this);
	}

}
