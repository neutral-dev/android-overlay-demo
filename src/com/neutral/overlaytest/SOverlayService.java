package com.neutral.overlaytest;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;

public class SOverlayService extends Service
{
	TextView label;
	WindowManager.LayoutParams overlay_layout;
	boolean shown;

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

		label = new TextView(this);
		label.setText("kekeke");
		label.setBackgroundColor(Color.BLACK);
		label.setTextColor(Color.rgb(99, 99, 99));

		overlay_layout = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
			PixelFormat.TRANSLUCENT);

		overlay_layout.gravity = Gravity.CENTER | Gravity.BOTTOM;
		overlay_layout.y = -50;

		((WindowManager) getSystemService(WINDOW_SERVICE)).addView(label, overlay_layout);
		shown = true;

		BroadcastReceiver lockBR = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i){
				if (i.getAction().equals(Intent.ACTION_SCREEN_OFF) && shown){
					((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(label);
					shown = false;
				} else if (i.getAction().equals(Intent.ACTION_USER_PRESENT) && !shown){
					((WindowManager) getSystemService(WINDOW_SERVICE)).addView(label, overlay_layout);
					shown = true;
				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);

		registerReceiver(lockBR, filter); 
    }

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if (label != null)
		{
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(label);
			label = null;
		}
	}
}
