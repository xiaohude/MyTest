package com.smarttiger.countries;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smarttiger.mytest.MainActivity;

public class CountryCode2GPS {
	
	private MainActivity main ;

	private static Map<String,CountryBean> mCountryMap;
	
	//通过国家码，获取这个国家地区中心的经纬度
	public CountryCode2GPS(MainActivity main) {
		this.main = main;

		main.showLog("挂载-------------国家码转中心GPS模块");
		main.showLog("命令: -mount gps");
		main.showLog("格式: CN、US、IN");
	}

	public boolean onClick(String text)
	{	
		if(TextUtils.isEmpty(text)){
			//手机默认设置的国家码
			text = main.getResources().getConfiguration().locale.getCountry(); 
		}
		getCountryLocal(text);
		return true;
	}
	
	
	private void getCountryLocal(String code) {
		CountryBean countryBean = get(code);
		if(countryBean == null)
			main.showLog("error code:"+code);
		else
			main.showLog(countryBean.toString());
	}
	

	public CountryBean get(String nameOrCode){
		if(nameOrCode == null || TextUtils.isEmpty(nameOrCode))
			return null;
		if(mCountryMap == null){
			buildMap();
		}
		return mCountryMap.get(nameOrCode.toLowerCase());
	}

	private void buildMap(){
		mCountryMap = new ConcurrentHashMap<>();
		
		String str = getFromAssets("countries_latitude_longitude.json");
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				String code = json.getString("code").toLowerCase();
				String name = json.getString("name").toLowerCase();
				double latitude = json.getDouble("latitude");
				double longitude = json.getDouble("longitude");
				
				CountryBean countryBean = new CountryBean(code, name, latitude, longitude);
				
				mCountryMap.put(code, countryBean);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String getFromAssets(String fileName){ 
        try { 
            InputStreamReader inputReader = new InputStreamReader(main.getResources().getAssets().open(fileName) ); 
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return null;
	} 
}
