package com.vipapp.apk_share.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vipapp.apk_share.fragment.InformationApkFragment;
import com.vipapp.apk_share.fragment.PermissionsAppFragment;

public class PagerAppAdapter extends FragmentStatePagerAdapter { 
	int mNumOfTabs; 

	public PagerAppAdapter(FragmentManager fm, int NumOfTabs) { 
		super(fm); 
		this.mNumOfTabs = NumOfTabs; 
	} 

	@Override 
	public Fragment getItem(int position) { 

		switch (position) { 
			case 0: 
				InformationApkFragment tab0 = new InformationApkFragment(); 
				return tab0; 
			case 1: 
				PermissionsAppFragment tab1 = new PermissionsAppFragment(); 
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
