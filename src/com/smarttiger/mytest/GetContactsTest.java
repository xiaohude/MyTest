package com.smarttiger.mytest;

import com.smarttiger.gethighlightacronymlib.FormatUtils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Profile;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.SearchSnippets;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

public class GetContactsTest {
	
	private MainActivity main;
	
	private static final String[] CONTACT_PROJECTION_PRIMARY = new String[] {
        Contacts._ID,                           // 0
        Contacts.DISPLAY_NAME_PRIMARY,          // 1
        Contacts.CONTACT_PRESENCE,              // 2
        Contacts.CONTACT_STATUS,                // 3
        Contacts.PHOTO_ID,                      // 4
        Contacts.PHOTO_THUMBNAIL_URI,           // 5
        Contacts.LOOKUP_KEY,                    // 6
        Contacts.NAME_RAW_CONTACT_ID,        	//7
    };
	
	private static final int CONTACT_ID               = 0;
	private static final int CONTACT_DISPLAY_NAME     = 1;
	private static final int CONTACT_PRESENCE_STATUS  = 2;
	private static final int CONTACT_CONTACT_STATUS   = 3;
	private static final int CONTACT_PHOTO_ID         = 4;
	private static final int CONTACT_PHOTO_URI        = 5;
	private static final int CONTACT_LOOKUP_KEY       = 6;
	private static final int CONTACT_NAME_RAW_CONTACT_ID = 7;

	

	private static final String[] PHONE_PROJECTION_PRIMARY = new String[] {
		Phone.CONTACT_ID,                    // 0
		Phone.DISPLAY_NAME_PRIMARY,          // 1
		Phone.CONTACT_PRESENCE,              // 2
		Phone.CONTACT_STATUS,                // 3
		Phone.PHOTO_ID,                      // 4
		Phone.PHOTO_THUMBNAIL_URI,           // 5
		Phone.LOOKUP_KEY,                    // 6
        Phone.NUMBER						 // 7
    };
	private static final String[] FILTER_PROJECTION_PRIMARY = new String[] {
        Contacts._ID,                           // 0
        Contacts.DISPLAY_NAME_PRIMARY,          // 1
        Contacts.CONTACT_PRESENCE,              // 2
        Contacts.CONTACT_STATUS,                // 3
        Contacts.PHOTO_ID,                      // 4
        Contacts.PHOTO_THUMBNAIL_URI,           // 5
        Contacts.LOOKUP_KEY,                    // 6
        SearchSnippets.SNIPPET,           		// 7
    };
    private static final int CONTACT_NUMBER			 = 7;
    
    
	
	public GetContactsTest(MainActivity main) {
		// TODO Auto-generated constructor stub
		this.main = main;

		main.showLog("挂载---------------获取本地联系人功能");
		main.showLog("命令: -mount getContacts");
		main.showLog("格式: -allphone、-me或要搜索的联系人信息 ");
	}
	
	public boolean onClick(String text)
	{	
		if(TextUtils.isEmpty(text))
			return false;
		
		if(text.equals("-allphone"))
			getContacts(text);
		else if(text.equals("-me"))
			getProfileContacts();
		else
			getFilterContacts(text);
		return true;
	}

	
	//获取‘我’的信息。
	private void getProfileContacts() {
		
		Cursor cursor = main.getContentResolver().query(Profile.CONTENT_URI,
				CONTACT_PROJECTION_PRIMARY, null, null, null);
		Contact contact = new Contact();
		
		if ((null != cursor) && (cursor.moveToFirst())) {
			contact.contactId = cursor.getLong(CONTACT_ID);
			contact.rawContactId = cursor.getLong(CONTACT_NAME_RAW_CONTACT_ID);
			contact.lookupKey = cursor.getString(CONTACT_LOOKUP_KEY);
			contact.displayName = cursor
					.getString(CONTACT_DISPLAY_NAME);
			contact.photoUri = cursor.getString(CONTACT_PHOTO_URI);
		}
		
		main.showLog("contactId=="+contact.contactId);
		main.showLog("rawContactId=="+contact.rawContactId);
		main.showLog("lookupKey=="+contact.lookupKey);
		main.showLog("displayName=="+contact.displayName);
		main.showLog("photoUri=="+contact.photoUri);
		
		cursor.close();
	}
	
	
	//获取所有联系人
	private void getContacts(String filter) {
//		String where = Phone.DISPLAY_NAME_PRIMARY + " LIKE '%"+filter+"%'" +  " OR REPLACE(REPLACE("+Phone.NUMBER+",' ',''),'-','') LIKE '%"+filter+"%'";
		String where = null;
		Cursor cursor = main.getContentResolver().query(Phone.CONTENT_URI, PHONE_PROJECTION_PRIMARY, where, null, Phone.SORT_KEY_PRIMARY);
	
		
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Contact contact = new Contact();
				contact.contactId = cursor.getLong(CONTACT_ID);
				contact.displayName = cursor.getString(CONTACT_DISPLAY_NAME);
				contact.number = cursor.getString(CONTACT_NUMBER);
				contact.photoUri = cursor.getString(CONTACT_PHOTO_URI);
				main.showLog("姓名："+contact.displayName+"--电话："+contact.number);
			}
		}
	}
	
	
	//搜索联系人
	private void getFilterContacts(String filter) {
		Uri uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(filter));
//		uri = uri.buildUpon().appendQueryParameter(SearchSnippets.DEFERRED_SNIPPETING_KEY, "1").build();
		
		Cursor cursor = main.getContentResolver().query(uri, FILTER_PROJECTION_PRIMARY, null, null, Contacts.SORT_KEY_PRIMARY);
	
		if (cursor != null) {
			SpannableStringBuilder spanTextBuilder =  new SpannableStringBuilder();
			while (cursor.moveToNext()) {
				Contact contact = new Contact();
				contact.contactId = cursor.getLong(CONTACT_ID);
				contact.displayName = cursor.getString(CONTACT_DISPLAY_NAME);
				contact.number = cursor.getString(CONTACT_NUMBER);
				contact.photoUri = cursor.getString(CONTACT_PHOTO_URI);

				//因为使用Contacts.CONTENT_FILTER_URI时有时候SNIPPET返回的是空
				if (TextUtils.isEmpty(contact.number)) {
					contact.number = queryPhoneNumberForContact(contact.contactId);
				}
				
//				String text = "姓名："+contact.displayName+"--电话："+contact.number;
				String text = contact.displayName+"----"+contact.number;
//				main.showLog(text);
				
		    	SpannableString spanText = new SpannableString(text);
//		    	int star = text.indexOf(filter);
		    	int star = FormatUtils.indexOfNameForSort(text, filter);
		    	if(star != -1)
		    		spanText.setSpan(new ForegroundColorSpan(0xff1499f7), star, star+filter.length(), 0);
		    	
		    	
		    	spanTextBuilder.append(spanText);
		    	spanTextBuilder.append("\n");
			}
			main.showLog(spanTextBuilder);
		}
	}
	
	/**
	 * 根据contactId查询获取其一个号码
	 * @param contactId
	 * @return 电话号码
	 */
	private String queryPhoneNumberForContact(long contactId) {
		Cursor cursor = null;
		try {
			String[] proj = new String[] {
					Phone.NUMBER
			};
			String where = Data.CONTACT_ID + "=? AND " + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'";
			cursor = main.getContentResolver().query(Data.CONTENT_URI, proj, where, new String[] {String.valueOf(contactId)}, null);
			if (cursor != null && cursor.moveToFirst()) {
				String number = cursor.getString(0);
				if (!TextUtils.isEmpty(number)) {
					return number;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				try {
					cursor.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
	
	public class Contact {
		long contactId = -1;
		long rawContactId = -1;
		String lookupKey = null;
		String displayName = null;
		String photoUri = null;
		String number = null;
	}
}
