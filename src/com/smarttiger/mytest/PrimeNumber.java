package com.smarttiger.mytest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.text.TextUtils;
import android.view.Gravity;

public class PrimeNumber {
	
	private MainActivity main ;
	private int base = 2;//需要转换的进制

	public PrimeNumber(MainActivity main) {
		this.main = main;
		main.logText.setGravity(Gravity.RIGHT);//设置为右对其，方便数字对比。
		
		main.showLog("挂载---------------获取素数功能");
		main.showLog("命令: -mount primeNumber");
		main.showLog("格式: -100（生成小于100的素数）、50（生成50个素数） ");
	}

	public boolean onClick(String text)
	{	
		if(TextUtils.isEmpty(text))
			return false;
		
		if(isNumeric(text))
			printPrimeNumbers(Integer.valueOf(text));
		else if (text.startsWith("-"))
			printLessPrimeNumbers(Integer.valueOf(text.substring(1)));
		return true;
	}
	

    private boolean isNumeric(String str){
		for(int i=str.length();--i>=0;){
			int chr=str.charAt(i);
			if(chr<48 || chr>57)
				return false;
		}
		return true;
	}

    //生成一个随机素数
	private void printPrimeNumber() {
		Random rnd = new Random(new Date().getTime());
//	    System.out.println(BigInteger.probablePrime(1024, rnd));//生成一个1024位的随机素数
	    main.showLog(""+BigInteger.probablePrime(9, rnd));
	    
	    printPrimeNumbers(100);
	}
	
	/**
	 * 生成固定个数素数。
	 * @param numberOfPrimes 生成素数的个数
	 */
	private void printPrimeNumbers(int numberOfPrimes) {
        int count = 0;
        int number = 2;
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        StringBuilder numbers = new StringBuilder();
        while (count < numberOfPrimes) {
            if (isPrime(number)) {
                count++;
                primeList.add(number);
                numbers.append(number + "--" + Integer.toString(number, base) +" \n");
            }
            number++;    
        }
        main.showLog(numbers);
    }
	
	/**
	 * 输出小于maxNumber的所有素数
	 * @param maxNumber 
	 */
	private void printLessPrimeNumbers(int maxNumber) {
        int number = 2;
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        StringBuilder numbers = new StringBuilder();
        while (number < maxNumber) {
            if (isPrime(number)) {
                primeList.add(number);
                numbers.append(number + "--" + Integer.toString(number, base) +" \n");
            }
            number++;    
        }
        main.showLog(numbers);
	}
 
	//判断是否为素数
	private boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
	
	
	
	//直接用Integer.toString(number, 3)就搞定了。
//	String sss = "";
//	//10进制转换为3进制
//	private int intTo3(int i) {
//		int s = i/3;
//		int y = i%3;
//		sss = y + sss;
//		if(s != 0)
//			intTo3(s);
//		
//		return s;
//	}
//	private String intTo33(int i) {
//		sss = "";
//		intTo3(i);
//		return sss;
//	}
}
