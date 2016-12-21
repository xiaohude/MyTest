package com.smarttiger.mytest;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class BatteryService extends Service {


	private BatteryBroadcastReciver receiver;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		receiver = new BatteryBroadcastReciver();  
		//创建一个过滤器  
		IntentFilter intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);  
		registerReceiver(receiver, intentFilter);

        Toast.makeText(this, "创建过滤器",Toast.LENGTH_LONG).show();
		
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "服务终止",Toast.LENGTH_LONG).show();
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
