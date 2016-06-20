package com.smarttiger.mytest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.os.Vibrator;

public class VibratorTest {
	
	private MainActivity main;

	/** �������Ϊ�����ֻ��𶯲����Ĵ��� */
	//�ǵ�<uses-permission android:name="android.permission.VIBRATE" />
	private Vibrator vibrator;
	private long[] pattern;
	private int repeat;
	private long milliseconds = 0;
	
	public VibratorTest(MainActivity main) {
		this.main = main;

		vibrator = (Vibrator) main.getSystemService(Service.VIBRATOR_SERVICE);
		main.showLog("����---------------������������Ч��");
		main.showLog("��ʽ: [75,50,75,50](-1) ���� 1000");
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
		main.showLog("��ʽ����");
		main.showLog("���Ը�ʽΪ [75,50,75,50](-1) ���� 1000");
	}
	
	public void cancel() {
		vibrator.cancel();
	}
	
	
	/** ��ʽΪ [75,50,75,50](-1) ���� 1000 */
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
		    Pattern p = Pattern.compile("\\[.*?\\]");// ���ҹ���ʽ�з��������ڵ��ַ�
		    Matcher m = p.matcher(text);
		    if (m.find()) {// �����ҵ������з�����
			    String param = m.group().replaceAll("\\[|\\]", "");// ȥ������
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
		    p = Pattern.compile("\\(.*?\\)");// ���ҹ���ʽ���������ڵ��ַ�
		    m = p.matcher(text);
		    if (m.find()) {// �����ҵ�����������
			    String param = m.group().replaceAll("\\(|\\)", "");// ȥ������
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
