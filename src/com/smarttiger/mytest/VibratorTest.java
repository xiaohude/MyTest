package com.smarttiger.mytest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.os.Vibrator;

public class VibratorTest {
	
	private MainActivity main;

	/** 下面代码为测试手机震动参数的代码 */
	//记得<uses-permission android:name="android.permission.VIBRATE" />
	private Vibrator vibrator;
	private long[] pattern;
	private int repeat;
	private long milliseconds = 0;
	
	public VibratorTest(MainActivity main) {
		this.main = main;

		vibrator = (Vibrator) main.getSystemService(Service.VIBRATOR_SERVICE);
		main.showLog("挂载---------------测试震动器参数效果");
		main.showLog("格式: [75,50,75,50](-1) 或者 1000");
	}
	
	
	public boolean onClick(String text) {
		
		vibrator.cancel();
		if(decodeVibratorString(text)) {
			if(milliseconds == 0)
				vibrator.vibrate(pattern, repeat);
			else
				vibrator.vibrate(milliseconds);
			return true;
		} else {
			return false;
		}
	}
	
	public void showSample() {
		main.showLog("格式错误！");
		main.showLog("测试格式为 [75,50,75,50](-1) 或者 1000");
	}
	
	public void cancel() {
		vibrator.cancel();
	}
	
	
	/** 格式为 [75,50,75,50](-1) 或者 1000 */
	private boolean decodeVibratorString(String text) {
		
		
		
		
		try {
			milliseconds = Long.valueOf(text);
			main.showLog("milliseconds=="+milliseconds);
			if(milliseconds != 0)
				return true;
		} catch (Exception e) {
			// TODO: handle exception
			milliseconds = 0;
		}
		
		try {
		    Pattern p = Pattern.compile("\\[.*?\\]");// 查找规则公式中方括号以内的字符
		    Matcher m = p.matcher(text);
		    if (m.find()) {// 遍历找到的所有方括号
			    String param = m.group().replaceAll("\\[|\\]", "");// 去掉括号
			    main.showLog(param);
			    String[] paramS = param.split(",");
			    pattern = new long[paramS.length];
			    for (int i = 0; i < paramS.length; i++) {
			    	pattern[i] = Long.valueOf(paramS[i]);
				}
			    main.showLog("pattern[]=="+pattern);
		    } else {
		    	return false;
		    }
		    p = Pattern.compile("\\(.*?\\)");// 查找规则公式中括号以内的字符
		    m = p.matcher(text);
		    if (m.find()) {// 遍历找到的所有括号
			    String param = m.group().replaceAll("\\(|\\)", "");// 去掉括号
			    repeat = new Integer(param);
			    main.showLog("repeat=="+repeat);
		    } else {
		    	return false;
		    }
		    return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}

}
