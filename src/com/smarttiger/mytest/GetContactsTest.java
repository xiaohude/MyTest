package com.smarttiger.mytest;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Profile;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.SearchSnippets;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

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
	
	private void getFilterContacts(String filter) {
		Uri uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(filter));
		Cursor cursor = main.getContentResolver().query(uri, FILTER_PROJECTION_PRIMARY, null, null, Contacts.SORT_KEY_PRIMARY);
	
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
	
	public class Contact {
		long contactId = -1;
		long rawContactId = -1;
		String lookupKey = null;
		String displayName = null;
		String photoUri = null;
		String number = null;
	}
}
