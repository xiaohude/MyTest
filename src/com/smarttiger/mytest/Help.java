package com.smarttiger.mytest;

public class Help {
	
	private MainActivity main;
	
	public Help(MainActivity main) {
		this.main = main;
	}
	
	public void showHelp() {
		main.showLog("---------------------------------Ŀǰ֧��---------------------------------");
		main.showLog("-help ��ʾ����");
		main.showLog("-clean  ���log");
		main.showLog("1.������������:");
		main.showLog("��ʽ: [75,50,75,50](-1) ���� 1000");
		main.showLog("2.��������Activity:");
		main.showLog("��ʽ: com.android.testActivity ���� ����+�س�+����");
		main.showLog("3.��ȡ������ϵ��");
		main.showLog("��ʽ: -allphone��-me��Ҫ��������ϵ����Ϣ ");
		
	}
	
}
