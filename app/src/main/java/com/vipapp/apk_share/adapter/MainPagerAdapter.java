package com.vipapp.apk_share.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vipapp.apk_share.fragment.InformationApkFragment;
import com.vipapp.apk_share.fragment.PermissionsAppFragment;
import com.vipapp.apk_share.fragment.InstalledAppFragment;
import com.vipapp.apk_share.fragment.FilesAppFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter { 
	int mNumOfTabs; 

	public MainPagerAdapter(FragmentManager fm, int NumOfTabs) { 
		super(fm); 
		this.mNumOfTabs = NumOfTabs; 
	} 
	@Override 
	public Fragment getItem(int position) { 
		switch (position) { 
			case 0: 
				InstalledAppFragment tab0 = new InstalledAppFragment(); 
				return tab0; 
			case 1: 
				FilesAppFragment tab1 = new FilesAppFragment(); 
				return tab1; 
			default: 
				return null; 
		} 
	} 
	@Override 
	public int getCount() { 
		return mNumOfTabs; 
	} 
} 
