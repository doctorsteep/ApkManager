package com.vipapp.apk_share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vipapp.apk_share.data.Data;

public class LibraryActivity extends AppCompatActivity {

	Toolbar toolbar;
	ListView list;
	private String[] listLibrary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Data.setThemeDefault(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_activity);
		list = (ListView) findViewById(R.id.list);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setTitle(getString(R.string.library));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true); 
		toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
		
		listLibrary = getResources().getStringArray(R.array.listLibrary);
		list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_library, R.id.nm, listLibrary));
		
	}
	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
