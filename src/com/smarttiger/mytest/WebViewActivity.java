package com.smarttiger.mytest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class WebViewActivity extends Activity {
	
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_webview);
		
//		String url = "https://m.baidu.com/s?wd=茄子快传";
//		String url = "https://www.google.com/search?q=SHAREit";
//		String url = "https://m.youtube.com/results?q=China";
		

		String url = getIntent().getStringExtra("Url");
		
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl(url);
		
		webView.setWebViewClient(new WebViewClient(){  
            @Override  
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
                // TODO Auto-generated method stub  
                view.loadUrl(url);  
                return true;//super.shouldOverrideUrlLoading(view, url);  
            }  
        });  
		
		webView.getSettings().setJavaScriptEnabled(true);  
		webView.getSettings().setPluginState(PluginState.ON);  
		webView.setWebChromeClient(new DefaultWebChromeClient());   
		
	}
	
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			 webView.goBack();// 返回前一个页面
			 return true;
		 }
		 return super.onKeyDown(keyCode, event);
	 }
	 

	 private class DefaultWebChromeClient extends WebChromeClient {  
        // 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职  
        CustomViewCallback customViewCallback;  
        // 进入全屏的时候  
        @Override  
        public void onShowCustomView(View view, CustomViewCallback callback) {  
            // 赋值给callback  
            customViewCallback = callback;  
            // 设置webView隐藏  
            webView.setVisibility(View.GONE);  
            // 声明video，把之后的视频放到这里面去  
            FrameLayout video = (FrameLayout) findViewById(R.id.videoContainer);  
            // 将video放到当前视图中  
            video.addView(view);  
            // 横屏显示  
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
            // 设置全屏  
            setFullScreen();  
        }  
        // 退出全屏的时候  
        @Override  
        public void onHideCustomView() {  
            if (customViewCallback != null) {  
                // 隐藏掉  
                customViewCallback.onCustomViewHidden();  
            }  
            // 用户当前的首选方向  
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);  
            // 退出全屏  
            quitFullScreen();  
            // 设置WebView可见  
            webView.setVisibility(View.VISIBLE);  
        }  
  
        @Override  
        public void onProgressChanged(WebView view, int newProgress) {  
            super.onProgressChanged(view, newProgress);  
        }  
    }  
	 
	 /** 
     * 设置全屏 
     */  
    private void setFullScreen() {  
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        // 全屏下的状态码：1098974464  
        // 窗口下的状态吗：1098973440  
    }  
  
    /** 
     * 退出全屏 
     */  
    private void quitFullScreen() {  
        // 声明当前屏幕状态的参数并获取  
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();  
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        getWindow().setAttributes(attrs);  
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  
    }  
}
