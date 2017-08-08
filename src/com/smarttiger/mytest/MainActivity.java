package com.smarttiger.mytest;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText editText;
	private Button testButton;
	private ScrollView scrollView;
	public TextView logText;
	private String logString = "";
	
	private Help help;
	private VibratorTest vibratorTest;
	private StartActivityTest startActivityTest;
	private GetContactsTest getContactsTest;
	private DebugTest debugTest;
	private PrimeNumber primeNumber;
	private BatteryBroadcastReciver receiver;
	private WebViewModule webViewModule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
		help = new Help(this);

		WindowManager wm = this.getWindowManager();
	    int width = wm.getDefaultDisplay().getWidth();
	    int height = wm.getDefaultDisplay().getHeight();
	    float dpi = getScreenDensity(this);
	    showLog("width===="+width+"---height===="+height+"----dpi===="+dpi);
	    showLog("");
		
//		mountAll();
		
//		debugTest = new DebugTest(this);

//		startActivityTest = new StartActivityTest(this);
//		startActivityTest.onClick("com.android.settings.DevelopmentSettings");
		
//		getContactsTest = new GetContactsTest(this);
		
//		primeNumber = new PrimeNumber(this);
		
//		Intent service = new Intent(this, BatteryService.class);  
//      this.startService(service);  

		webViewModule = new WebViewModule(this);
	}
	
	/** 获取屏幕密度 */
    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
    
	private void initView() {
		editText = (EditText) findViewById(R.id.edit_text);
		testButton = (Button) findViewById(R.id.test_button);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		logText = (TextView) findViewById(R.id.log_text);
		handler = new Handler();
		
		testButton.setOnClickListener(new TestOnClickListener());
		logText.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				//解决弹出键盘后，logText被遮挡没有自动滑倒最低端。
				scrollDown();
			}
		});
	}
	
	public void onCleanEdit(View view) {
		editText.setText("");
	}
	
	public void showLog(String log) {
		logString = logString + log + "\n";
		logText.setText(logString);
		scrollDown();

		spanText.append(log);//同步span字符串
		spanText.append("\n");
		logBuilder.append(log);//同步logBuilder字符串
		logBuilder.append("\n");
	}
	
	private StringBuilder logBuilder =  new StringBuilder();
	public void showLog(StringBuilder log) {
		logBuilder.append(log);
		logBuilder.append("\n");
		logText.setText(logBuilder);
		scrollDown();
		
		logString = logString + log + "\n";//同步普通字符串
	}
	
	private SpannableStringBuilder spanText =  new SpannableStringBuilder();
	/** 用来显示可变色的文字 */
	public void showLog(SpannableStringBuilder log) {
		spanText.append(log);
		spanText.append("\n");
		logText.setText(spanText);
		scrollDown();
		
		logString = logString + log + "\n";//同步普通字符串
	}
	
	Handler handler;
	private void scrollDown() {
		//因为Android很多函数都是基于消息队列来同步，所以需要异步操作，
		//addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll， view可能还没有显示出来，
		//所以会失败，或者没有到最低端
		//应该通过handler在新线程中更新
		handler.post(new Runnable() {  
		    @Override  
		    public void run() {  
		        scrollView.fullScroll(ScrollView.FOCUS_DOWN);  
		    }  
		});
	}
	
	/** 挂载所有模块 */
	private void mountAll() {
		vibratorTest = new VibratorTest(this);
		startActivityTest = new StartActivityTest(this);
		getContactsTest = new GetContactsTest(this);
		debugTest = new DebugTest(this);
		primeNumber = new PrimeNumber(this);
		webViewModule = new WebViewModule(this);
	}
	/** 清空所有模块 */
	private void cleanAll() {
		vibratorTest = null;
		startActivityTest = null;
		getContactsTest = null;
		debugTest = null;
		primeNumber = null;
		webViewModule = null;
		logText.setGravity(Gravity.LEFT);//还原为左对其
		showLog("清空所有挂载");
	}
	private void mount(String text) {
		String s = text.substring(text.indexOf(" ")+1);
		if(s.equals("all"))
			mountAll();
		else if(s.equals("clean"))
			cleanAll();
		else if(s.equals("vibrator"))
			vibratorTest = new VibratorTest(this);
		else if(s.equals("startActivity"))
			startActivityTest = new StartActivityTest(this);
		else if(s.equals("getContacts"))
			getContactsTest = new GetContactsTest(this);
		else if(s.equals("debugTest"))
			debugTest = new DebugTest(this);
		else if(s.equals("primeNumber"))
			primeNumber = new PrimeNumber(this);
		else if(s.equals("webView"))
			webViewModule = new WebViewModule(this);
	}
	
	class TestOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			showLog("");
			
			String text = editText.getText().toString();
			if(text.startsWith("-")) {
				if(text.equals("-help")) {
					help.showHelp();
					return;
				}
				if(text.equals("-clean")) {
					logString = "";
					showLog("");
					return;
				}
				if(text.startsWith("-mount")) {
					mount(text);
					return;
				}
			}
			
			if(vibratorTest == null || !vibratorTest.onClick(text))
			if(startActivityTest == null || !startActivityTest.onClick(text))
			if(getContactsTest ==null || !getContactsTest.onClick(text))
			if(debugTest ==null || !debugTest.onClick(text))
			if(primeNumber ==null || !primeNumber.onClick(text))
			if(webViewModule ==null || !webViewModule.onClick(text))
			{
				showLog("格式错误！");
				help.showHelp();
				
//				ShowLongDialog.show(MainActivity.this);
			}
			
			
		}
		
	}
	

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(vibratorTest != null)
			vibratorTest.cancel();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		unregisterReceiver(receiver);
	}
}
