# MyTest
用于方便测试代码，界面简单，一个输入框一个按钮一个log输出框。

-help 显示帮助<br>
-clean  清空log<br>
-mount all    挂载所有模块<br>
-mount clean  清空所有挂载<br>
-mount xxxx   挂载单个模块<br>
1.震动器参数测试:<br>
-mount vibrator<br>
格式: \[75,50,75,50\]\(-1\) 或者 1000<br>
2.开启三方Activity:<br>
-mount startActivity<br>
格式: com.android.testActivity 或者 包名+回车+类名<br>
3.获取本机联系人<br>
-mount getContacts<br>
格式: -allphone、-me或要搜索的联系人信息 <br>
4.调试三方App参数:<br>
-mount debugTest<br>
格式: isTest、hasPadding、intTest66<br>
5.获取素数功能:<br>
-mount primeNumber<br>
格式: -100（生成小于100的素数）、50（生成50个素数）<br>

截图：
![截图](./ScreenShots/main.png)
