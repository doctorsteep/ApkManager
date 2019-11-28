package com.vipapp.apk_share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

	Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Data.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setTitle(getString(R.string.settings));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
		
		getFragmentManager().beginTransaction()
            .add(R.id.contentSettings, new SettingsFragment())
            .commit();
	}
	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
