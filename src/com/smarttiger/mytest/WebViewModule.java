package com.smarttiger.mytest;


import android.content.Intent;
import android.text.TextUtils;

public class WebViewModule {
	
	private MainActivity main ;

	public WebViewModule(MainActivity main) {
		this.main = main;
		
		main.showLog("挂载---------------WebView测试模块");
		main.showLog("命令: -mount webView");
		main.showLog("格式: https://m.youtube.com/results?q=China");
		
	}

	public boolean onClick(String text)
	{	
		if(TextUtils.isEmpty(text))
			return false;

        Intent intent = new Intent(main, WebViewActivity.class);
        intent.putExtra("Url", text);
        main.startActivity(intent);
		
		return true;
	}
	
}
