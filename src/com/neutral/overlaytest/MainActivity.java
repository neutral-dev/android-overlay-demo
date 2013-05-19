package com.neutral.overlaytest;

import android.app.*;
import android.os.*;
import android.content.*;

public class MainActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Toast toast = Toast.makeText(this, "onCreate!activity", Toast.LENGTH_LONG);
		// toast.show();
		
		/*
		Replace OverlayService for changing overlay type.
		
		OverlayService - TYPE_SYSTEM_ERROR, overlays all, captures touch events, but
		blocks in-app brightness controls.
		AlertService - TYPE_SYSTEM_ALERT, overlays all except system UI, captures
		touch events.
		SOverlayService - TYPE_SYSTEM_OVERLAY, overlays all, doesn't capture touch
		events.
		*/
		
		Intent service = new Intent(this, OverlayService.class);
		startService(service);
	}
}
