package com.neutral.overlaytest;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;

public class AlertService extends Service
{
	Button button;
	WindowManager.LayoutParams overlay_layout;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

    @Override
    public void onCreate()
	{
        super.onCreate();

		// Toast toast = Toast.makeText(getBaseContext(), "onCreate!service", Toast.LENGTH_LONG);
		// toast.show();

		button = new Button(this);
		button.setText("kekeke");
		button.setBackgroundColor(Color.BLACK);
		button.setTextColor(Color.rgb(99, 99, 99));

		button.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent e)
				{
					switch (e.getAction()) {
						case (MotionEvent.ACTION_DOWN): {
								((Button) v).setBackgroundColor(Color.DKGRAY);
								return true;
							}
						case (MotionEvent.ACTION_UP): {
								((Button) v).setBackgroundColor(Color.BLACK);
								return true;
							}
						default: {
								return false;
							}
					}
				}
			} );

		overlay_layout = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
			PixelFormat.TRANSLUCENT);

		overlay_layout.gravity = Gravity.CENTER | Gravity.BOTTOM;

		((WindowManager) getSystemService(WINDOW_SERVICE)).addView(button, overlay_layout);
    }

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if (button != null)
		{
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(button);
			button = null;
		}
	}
}
