package com.smarttiger.mytest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;

public class StartActivityTest {
	

	private MainActivity main;
	public StartActivityTest(MainActivity main) {
		this.main = main;

		main.showLog("����---------------��������Activity����");
		main.showLog("��ʽ: com.test.testActivity ���� ����+�س�+����");
	}
	
	public boolean onClick(String text) {
		
		return decodeStartActivityString(text);
	}
	

	private boolean decodeStartActivityString(String text) {
		Pattern p = Pattern.compile(".*\\..*\\..*");
	    Matcher m = p.matcher(text);
	    
	    String packageName = "";
	    String className = "";
	    if(m.find())
	    {
	    	if(text.contains("\n")) {
	    		packageName = text.substring(0, text.lastIndexOf("\n"));
	    		className = text.substring(text.lastIndexOf("\n") + 1);
	    	}
	    	else {
	    		packageName = text.substring(0, text.lastIndexOf("."));
	    		className = text;
	    	}
	    	try {
				Intent intent = new Intent();
				intent.setClassName(packageName, className);
				main.startActivity(intent);
				main.showLog("startActivity:\n"+"packageName=="+packageName+"\nclassName=="+className);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				main.showLog("Have no class:\n"+"packageName=="+packageName+"\nclassName=="+className);
				return true;
			}
	    }
		
		return false;
	}
	
}
