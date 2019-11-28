package com.vipapp.apk_share;

import android.graphics.drawable.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.vipapp.apk_share.adapter.*;
import com.vipapp.apk_share.data.*;
import com.vipapp.apk_share.model.*;
import org.vipapp.design.fm.util.*;

public class ViewActivity extends AppCompatActivity {

	TabLayout tabLayout;
	ViewPager viewPager;
	
	String pkg = "";
	ImageView icon;
	TextView name;
	TextView pack;
	LinearLayout lBar;
	TextView lName;
	ImageView imgMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Data.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		icon = (ImageView) findViewById(R.id.icon);
		name = (TextView) findViewById(R.id.name);
		pack = (TextView) findViewById(R.id.pack);
		imgMenu = (ImageView) findViewById(R.id.ImageMenu);
		lBar = (LinearLayout) findViewById(R.id.lBar);
		lName = (TextView) findViewById(R.id.lName);
		AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
		
		pkg = getIntent().getStringExtra("pkg");
		ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(this);
		
		tabLayout.setTranslationX(-60f);
		tabLayout.setAlpha(0.0f);
		viewPager.setTranslationY(300f);
		viewPager.setAlpha(0.0f);
		icon.setTranslationX(50f);
		icon.setAlpha(0.0f);
		name.setAlpha(0.0f);
		name.setTranslationX(-60f);
		pack.setAlpha(0.0f);
		pack.setTranslationX(-60f);
		imgMenu.setTranslationX(50f);
		imgMenu.setAlpha(0.0f);
		lBar.setAlpha(0.0f);
		lBar.setTranslationY(-50f);
		String pkgName = apkInfoExtractor.GetAppName(pkg);
		name.setTypeface(FMTypeface.getTypeface(this, "black"));
		lName.setTypeface(FMTypeface.getTypeface(this, "bold"));
		
		Drawable pkgDrawable = apkInfoExtractor.getAppIconByPackageName(pkg);
		
		name.setText(pkgName);
		lName.setText(pkgName);
		pack.setText(pkg);
		icon.setImageDrawable(pkgDrawable);
		
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
				@Override
				public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
					if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
						//Toast.makeText(getApplicationContext(), "Hide 1", Toast.LENGTH_SHORT).show();
						setBar("show");
					} else if (verticalOffset == 0) {
						//Toast.makeText(getApplicationContext(), "Hide 2", Toast.LENGTH_SHORT).show();
						setBar("hide");
					} else {
						
					}
				}
			});
		
		imgMenu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
					new Apk().setAppMenu(ViewActivity.this, pkg, 1);
				}
			});
			
		tabLayout.addTab(tabLayout.newTab().setText(R.string.information)); 
		tabLayout.addTab(tabLayout.newTab().setText(R.string.all_permissions));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); 
		
		final PagerAppAdapter adapter = new PagerAppAdapter(getSupportFragmentManager(), tabLayout.getTabCount()); 
        viewPager.setAdapter(adapter); 
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); 
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { 
				@Override 
				public void onTabSelected(TabLayout.Tab tab) { 
					viewPager.setCurrentItem(tab.getPosition()); 
				} 
				@Override 
				public void onTabUnselected(TabLayout.Tab tab) { } 
				@Override 
				public void onTabReselected(TabLayout.Tab tab) { } 
			});
			
		
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
	@Override
	protected void onStart() {
		setLoadStart();
		super.onStart();
	}
	
	private void setLoadStart() {
		tabLayout.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		tabLayout.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		viewPager.animate().translationY(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		viewPager.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		icon.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		icon.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		name.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		name.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		pack.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		pack.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		imgMenu.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
		imgMenu.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
	}
	
	private void setBar(String type) {
		switch(type) {
			case "show":
				lBar.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
				lBar.animate().translationY(0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
				break;
			case "hide":
				lBar.animate().alpha(0.0f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
				lBar.animate().translationY(-50f).setDuration(Data.getSpeedAnimation(getApplicationContext())).start();
				break;
		}
	}
}
