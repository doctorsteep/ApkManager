package com.vipapp.apk_share.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.model.ApkInfoExtractor;
import android.widget.ScrollView;
import org.vipapp.design.fm.util.*;
import com.vipapp.apk_share.data.Apk;

public class InformationApkFragment extends Fragment { 

	public String pkg = "";
	
	TextView versionName;
	TextView versionCode;
	TextView lastUpdateTime;
	TextView installTime;
	TextView developer;
	TextView size;
	TextView type_app;
	
	TextView textI1;
	
	LinearLayout linI1;
	
	LinearLayout linAppSize;
	
	ScrollView scroll;

    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        View view = inflater.inflate(R.layout.information, container, false); 
		
		versionName = (TextView) view.findViewById(R.id.versionName);
		versionCode = (TextView) view.findViewById(R.id.versionCode);
		lastUpdateTime = (TextView) view.findViewById(R.id.lastUpdateTime);
		installTime = (TextView) view.findViewById(R.id.installTime);
		developer = (TextView) view.findViewById(R.id.developer);
		size = (TextView) view.findViewById(R.id.size);
		type_app = (TextView) view.findViewById(R.id.type_app);
		textI1 = (TextView) view.findViewById(R.id.textI1);
		scroll = (ScrollView) view.findViewById(R.id.scroll);
		linI1 = (LinearLayout) view.findViewById(R.id.linI1);
		linAppSize = (LinearLayout) view.findViewById(R.id.linAppSize);

		pkg = getActivity().getIntent().getStringExtra("pkg");
		
		scroll.setNestedScrollingEnabled(true);
		textI1.setTypeface(FMTypeface.getTypeface(getActivity(), "bold"));
		
		try {
			linI1.setClipToOutline(true);
		} catch(Exception e) {}
		
		ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(getActivity());
		
		String pkgVersion = apkInfoExtractor.GetAppVersion(pkg);
		String pkgVersionCode = apkInfoExtractor.GetAppVersionCode(pkg);
		String pkgLastUpdateTime = apkInfoExtractor.GetAppLastUpdateTime(pkg);
		String pkgInstallTime = apkInfoExtractor.GetAppInstallTime(pkg);
		String pkgSize = apkInfoExtractor.GetAppSize(pkg);
		
		versionName.setText(pkgVersion);
		versionCode.setText(pkgVersionCode);
		lastUpdateTime.setText(pkgLastUpdateTime);
		installTime.setText(pkgInstallTime);
		size.setText(pkgSize);
		developer.setText(apkInfoExtractor.getAppDeveloper(pkg));
		if(!apkInfoExtractor.isSystemApp(pkg)) {
			type_app.setText(getString(R.string.type_app_system));
		} else {
			type_app.setText(getString(R.string.type_app_not_system));
		}
		
		linAppSize.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
					new Apk().setApkSizeMenu(getActivity(), pkg);
				}
			});
		return view; 
	} 

} 
