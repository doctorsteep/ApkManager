package com.vipapp.apk_share.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.vipapp.apk_share.R;

public class ListData {
	
	public static int getColummsList(Context ctx) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		String listValue = sp.getString("listKey", "");
		int listInt = 1;
		
		switch (listValue) {
			case "1":
				listInt = Data.getNumberOfColumns(ctx);
				break;
			case "2":
				listInt = Data.getNumberOfColumns(ctx);
				break;
		}
		return listInt;
	}
	
	public static int getLayoutList(Context ctx) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		String listValue = sp.getString("listKey", "");
		int listInt = 1;

		switch (listValue) {
			case "1":
				listInt = R.layout.list_app;
				break;
			case "2":
				listInt = R.layout.list_app_table;
				break;
		}
		return listInt;
	}
}
