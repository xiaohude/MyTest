package com.smarttiger.mytest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class DebugTest {
	

	private MainActivity main;

	private SharedPreferences preferences;
	private boolean isTest = false;
	private boolean hasPadding = false;
	
	public DebugTest(MainActivity main) {
		this.main = main;

		preferences = main.getSharedPreferences("debug_parameter", Activity.MODE_WORLD_READABLE);
		isTest = preferences.getBoolean("isTest", false);
		hasPadding = preferences.getBoolean("hasPadding", false);

		main.showLog("挂载---------------调试三方App参数");
		main.showLog("命令: -mount debugTest");
		main.showLog("格式: isTest、hasPadding");
	}
	
	
	//在其他项目中直接使用下面代码即可获取到参数。可以在调试代码的onResume里调用
	//调试代码需删除
//	boolean isTest;
//	private void debugTest() {
//		try {
//			Context myTestContext = createPackageContext("com.smarttiger.mytest",Context.CONTEXT_IGNORE_SECURITY);  
//	        SharedPreferences pref = myTestContext.getSharedPreferences("debug_parameter", Context.MODE_MULTI_PROCESS);  
//	        isTest = pref.getBoolean("isTest", false);  
//		} catch (Exception e) {
//			// TODO: handle exception
//	        System.out.println("Can not create Context 'com.smarttiger.mytest' ");
//		}
//	}
	
	
	public boolean onClick(String text) {
		
		return decodeDebugTestString(text);
	}
	

	private boolean decodeDebugTestString(String text) {
		if(TextUtils.isEmpty(text))
			return false;
		if(text.equals("isTest")) {
			isTest = !isTest;
			Editor editor = preferences.edit();
			editor.putBoolean("isTest", isTest);
			editor.commit();
			main.showLog("isTest=="+isTest);
			return true;
		}
		if(text.equals("hasPadding")) {
			hasPadding = !hasPadding;
			Editor editor = preferences.edit();
			editor.putBoolean("hasPadding", hasPadding);
			editor.commit();
			main.showLog("hasPadding==="+hasPadding);
			return true;
		}
		
		return false;
	}
	
}
