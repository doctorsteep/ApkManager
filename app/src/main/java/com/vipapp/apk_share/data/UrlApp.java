package com.vipapp.apk_share.data;

import android.os.Bundle;
import android.net.Uri;
import android.widget.Toast;
import android.app.Activity;

public class UrlApp extends Activity {

	private Uri url;
	private String pkg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		try {
			GO();
		} catch (Exception e) {
			finishAffinity();
			Toast.makeText(getApplicationContext(), "Error: Please, check url - " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	private void GO() {
		url = getIntent().getData();
		pkg = url.toString();

		pkg = pkg.replaceAll("http://app-", "");
		pkg = pkg.replaceAll("-by", "");

		new Apk().setAppMenu(getApplicationContext(), pkg, 0);
		
		Toast.makeText(getApplicationContext(), pkg, Toast.LENGTH_SHORT).show();
	}
}
