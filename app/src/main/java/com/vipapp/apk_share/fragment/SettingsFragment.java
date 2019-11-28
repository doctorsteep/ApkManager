package com.vipapp.apk_share.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.model.ApkInfoExtractor;
import android.content.Intent;
import com.vipapp.apk_share.LibraryActivity;

public class SettingsFragment extends PreferenceFragment {
	
	private String PACKAGE_APP = "com.vipapp.apk_share";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		Preference versionScreen = findPreference("versionScreen");
		Preference libraryScreen = findPreference("libraryScreen");
		Preference listKey = findPreference("listKey");
		
		ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(getActivity());
		
		listKey.setEnabled(false);
		
		versionScreen.setSummary(String.format(getString(R.string.app_version_desc), apkInfoExtractor.GetAppVersion(PACKAGE_APP)));
		
		versionScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Data.searchAppGooglePlay(getActivity(), PACKAGE_APP);
					return false;
				}
			});
		libraryScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					getActivity().startActivity(new Intent(getActivity(), LibraryActivity.class));
					return false;
				}
			});
    }
}
