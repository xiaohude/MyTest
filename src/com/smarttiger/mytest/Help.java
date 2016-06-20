package com.smarttiger.mytest;

public class Help {
	
	private MainActivity main;
	
	public Help(MainActivity main) {
		this.main = main;
	}
	
	public void showHelp() {
		main.showLog("---------------------------------目前支持---------------------------------");
		main.showLog("-help 显示帮助");
		main.showLog("-clean  清空log");
		main.showLog("1.震动器参数测试:");
		main.showLog("格式: [75,50,75,50](-1) 或者 1000");
		main.showLog("2.开启三方Activity:");
		main.showLog("格式: com.android.testActivity 或者 包名+回车+类名");
		main.showLog("3.获取本机联系人");
		main.showLog("格式: -allphone、-me或要搜索的联系人信息 ");
		
	}
	
}
