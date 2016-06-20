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
		
		vibratorTest = new VibratorTest(this);
		startActivityTest = new StartActivityTest(this);
		getContactsTest = new GetContactsTest(this);
		
		
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
		//��ΪAndroid�ܶຯ�����ǻ�����Ϣ������ͬ����������Ҫ�첽������
		//addView��֮�󣬲��������Ͼͻ���ʾ�������ڶ����еȴ�������Ȼ�ܿ죬���������������fullScroll�� view���ܻ�û����ʾ������
		//���Ի�ʧ�ܣ�����û�е���Ͷ�
		//Ӧ��ͨ��handler�����߳��и���
		handler.post(new Runnable() {  
		    @Override  
		    public void run() {  
		        scrollView.fullScroll(ScrollView.FOCUS_DOWN);  
		    }  
		});
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
			}
			
			if(vibratorTest == null || !vibratorTest.onClick(text))
			if(startActivityTest == null || !startActivityTest.onClick(text))
			if(getContactsTest ==null || !getContactsTest.onClick(text))
			{
				showLog("��ʽ����");
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
