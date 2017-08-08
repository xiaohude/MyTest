package com.smarttiger.mytest;

public class Help {
	
	private MainActivity main;
	
	public Help(MainActivity main) {
		this.main = main;
	}
	
	public void showHelp() {
		main.showLog("---------------------------------目前支持----------------------------");
		main.showLog("-help 显示帮助");
		main.showLog("-clean  清空log");
		main.showLog("-mount all    挂载所有模块");
		main.showLog("-mount clean  清空所有挂载");
		main.showLog("-mount xxxx   挂载单个模块");
		main.showLog("1.震动器参数测试:");
		main.showLog("命令：-mount vibrator");
		main.showLog("格式：[75,50,75,50](-1) 或者 1000");
		main.showLog("2.开启三方Activity:");
		main.showLog("命令：-mount startActivity");
		main.showLog("格式：com.android.testActivity 或者 包名+回车+类名");
		main.showLog("3.获取本机联系人:");
		main.showLog("命令：-mount getContacts");
		main.showLog("格式：-allphone、-me或要搜索的联系人信息 ");
		main.showLog("4.调试三方App参数:");
		main.showLog("命令：-mount debugTest");
		main.showLog("格式：isTest、hasPadding、intTest44");
		main.showLog("5.获取素数功能:");
		main.showLog("命令: -mount primeNumber");
		main.showLog("格式: -100（生成小于100的素数）、50（生成50个素数） ");
		main.showLog("6.WebView测试模块");
		main.showLog("命令: -mount webView");
		main.showLog("格式: https://m.youtube.com/results?q=China");
		
	}
	
}
