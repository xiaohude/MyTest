package com.smarttiger.mytest;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.BatteryManager;
import android.text.format.Time;
import android.widget.Toast;

// 记得添加权限<uses-permission android:name="android.permission.BATTERY_STATS"/> 
public class BatteryBroadcastReciver extends BroadcastReceiver {

	private MainActivity main;
	private Time time;
	
	int i = 0;
	
	public BatteryBroadcastReciver() {
		time = new Time();
	}
	
	public BatteryBroadcastReciver(MainActivity main) {
		// TODO Auto-generated constructor stub
		this.main = main;
		time = new Time();
		
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            Toast.makeText(context, "网络状态变化",Toast.LENGTH_LONG).show();
		}
		if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
			time.setToNow();  
    		int hour = time.hour;
            Toast.makeText(context, "当前电量已小于15%",Toast.LENGTH_LONG).show();
            if(hour >= 9 && hour < 22) {
            	//提示用户
            	MediaPlayer mp = MediaPlayer.create(context, R.raw.nokia_lesspower);
                mp.start();//开始播放  
            	i = 0;
                mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						if (i > 6)
							return;
						i++;
						mp.start();
					}
				});
            }
		}
		// ACTION_BATTERY_CHANGED属于不能静态注册的广播，静态注册不起作用，只能动态注册。
		// 但是ACTION_BATTERY_LOW可以静态注册。
		if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
            //得到系统当前电量
            int level=intent.getIntExtra("level", 0);
            //取得系统总电量
            int total=intent.getIntExtra("scale", 100);

//            Toast.makeText(context, "当前电量："+(level*100)/total+"%",Toast.LENGTH_LONG).show();
//            main.showLog("当前电量："+(level*100)/total+"%");
            
            int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
//            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
//            	main.showLog("充电中");
            
            time.setToNow();  
    		int hour = time.hour;
    		int minute = time.minute;
    		int sec = time.second;

//    		main.showLog("当前时间为：" 
//						+ hour  + "时 "
//						+ minute+ "分 " 
//						+ sec   + "秒"); 

    		
            //当电量小于15%时触发
            if(level <= 20 && status != BatteryManager.BATTERY_STATUS_CHARGING){
                Toast.makeText(context, "当前电量已小于20%",Toast.LENGTH_LONG).show();
                if(hour >= 9 && hour < 22) {
                	//提示用户
                	
                	MediaPlayer mp = MediaPlayer.create(context, R.raw.nokia_lesspower);
                    mp.start();//开始播放  
                	i = 0;
                    
                    mp.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							if (i > 6)
								return;
							i++;
							mp.start();
						}
					});
                }
            }
            
        }
		
		
		//开机自启动监听
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			
			
			Intent service = new Intent(context, BatteryService.class);  
            context.startService(service);  

            Toast.makeText(context, "低电量监听启动",Toast.LENGTH_LONG).show();
			
		}
		
		
	}

}
