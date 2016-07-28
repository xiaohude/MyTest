package com.smarttiger.mytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ShowLongDialog {
	
	/** 显示一个可滚动的大Dialog */
	public static void show(Activity activity) {
		// TODO Auto-generated constructor stub

		View view = activity.getLayoutInflater().inflate(R.layout.regulatory_info, null);
		AlertDialog alert = new AlertDialog.Builder(activity)
		.setTitle("Regulatory information")
		.setView(view)
        .show();
		
//		alert.getWindow().getDecorView().setPadding(0, 0, 0, 0); 
//		WindowManager windowManager = getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
//		WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
//		lp.width = (int)(display.getWidth()); //设置宽度
//		alert.getWindow().setAttributes(lp);
		
		//缩小dialog的边间距。使dialog大一些。
		Window win = alert.getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		win.setAttributes(lp);
	}
}
