package com.vipapp.apk_share.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vipapp.apk_share.MainActivity;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.adapter.AppsAdapter;
import com.vipapp.apk_share.adapter.MainPagerAdapter;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.model.ApkInfoExtractor;
import org.vipapp.design.fm.util.FMTypeface;
import android.widget.ImageView;
import org.vipapp.design.fm.util.FontName;
import com.vipapp.apk_share.data.ThreadLoaded;

public class MainFragment extends Fragment { 

	RecyclerView.Adapter adapter;
	TextView title;
	ImageView imgSettings;
	
	TabLayout tabLayout;
	ViewPager viewPager;

    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        View view = inflater.inflate(R.layout.main_fragment, container, false);
		setHasOptionsMenu(true);
		title = (TextView) view.findViewById(R.id.title);
		imgSettings = (ImageView) view.findViewById(R.id.ImageSettings);
		tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		
		adapter = new AppsAdapter(getActivity(), new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());
		title.setTypeface(FMTypeface.getTypeface(getActivity(), FontName.BLACK));
		title.setText(getString(R.string.app_name) + " - Apps (" + adapter.getItemCount() + ")");
		
		tabLayout.setTranslationX(-60f);
		tabLayout.setAlpha(0.0f);
		viewPager.setTranslationY(300f);
		viewPager.setAlpha(0.0f);
		title.setAlpha(0.0f);
		title.setTranslationX(-60f);
		imgSettings.setTranslationX(50f);
		imgSettings.setAlpha(0.0f);
		
		tabLayout.addTab(tabLayout.newTab().setText(R.string.app)); 
		tabLayout.addTab(tabLayout.newTab().setText(R.string.apk));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); 

		final MainPagerAdapter adapter = new MainPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager(), tabLayout.getTabCount()); 
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
			
		imgSettings.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
					new MainActivity().setSettings(getActivity());
				}
			});
		return view; 
	}
	@Override
	public void onStart() {
		setLoadStart();
		super.onStart();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.settings:
				new MainActivity().setSettings(getActivity());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.context_main, menu);
	}
	
	
	private void setLoadStart() {
		tabLayout.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		tabLayout.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		viewPager.animate().translationY(0.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		viewPager.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		title.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		title.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		imgSettings.animate().translationX(0.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
		imgSettings.animate().alpha(1.0f).setDuration(Data.getSpeedAnimation(getActivity())).start();
	}
}
