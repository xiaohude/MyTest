package com.smarttiger.mytest;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText editText;
	private Button testButton;
	private ScrollView scrollView;
	private TextView logText;
	private String logString = "";
	
	private Help help;
	private VibratorTest vibratorTest;
	private StartActivityTest startActivityTest;
	private GetContactsTest getContactsTest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
		help = new Help(this);
		
		
		mountAll();
		
	}
	
	private void initView() {
		editText = (EditText) findViewById(R.id.edit_text);
		testButton = (Button) findViewById(R.id.test_button);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		logText = (TextView) findViewById(R.id.log_text);
		handler = new Handler();
		
		testButton.setOnClickListener(new TestOnClickListener());
	}
	
	public void showLog(String log) {
		logString = logString + log + "\n";
		logText.setText(logString);
		scrollDown();
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
	}
	/** 清空所有模块 */
	private void cleanAll() {
		vibratorTest = null;
		startActivityTest = null;
		getContactsTest = null;
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
			{
				showLog("格式错误！");
				help.showHelp();
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
	
}
