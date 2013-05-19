package com.neutral.overlaytest;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;

public class OverlayService extends Service
{
	Button button;
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
			WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
			PixelFormat.TRANSLUCENT);
			
		overlay_layout.gravity = Gravity.CENTER | Gravity.BOTTOM;
		overlay_layout.y = -50;
		
		((WindowManager) getSystemService(WINDOW_SERVICE)).addView(button, overlay_layout);
		shown = true;
		
		BroadcastReceiver lockBR = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i){
				if (i.getAction().equals(Intent.ACTION_SCREEN_OFF) && shown){
					((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(button);
					shown = false;
				} else if (i.getAction().equals(Intent.ACTION_USER_PRESENT) && !shown){
					((WindowManager) getSystemService(WINDOW_SERVICE)).addView(button, overlay_layout);
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
		
		if (button != null)
		{
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(button);
			button = null;
		}
	}
}
