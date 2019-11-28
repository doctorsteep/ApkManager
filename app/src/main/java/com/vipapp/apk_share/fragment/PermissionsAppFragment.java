package com.vipapp.apk_share.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.permission.InfoPermission;
import java.util.ArrayList;
import java.util.List;

public class PermissionsAppFragment extends Fragment { 

	public String pkg = "";
	
	ListView list;
	
    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        View view = inflater.inflate(R.layout.permissions, container, false); 
		list = (ListView) view.findViewById(R.id.list);
		list.setNestedScrollingEnabled(true);
		
		pkg = getActivity().getIntent().getStringExtra("pkg");

		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_permission, R.id.nm, getGrantedPermissions(pkg));
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					String selectedFromList = (String)(list.getItemAtPosition(position));
					InfoPermission.setPermissionMenu(getActivity(), pkg, selectedFromList);
				}
			});
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					String selectedFromList = (String)(list.getItemAtPosition(p3));
					Data.setClipboard(getActivity(), selectedFromList);
					return true;
				}
			});
		
		return view; 
	} 
	List<String> getGrantedPermissions(final String appPackage) {
		List<String> granted = new ArrayList<String>();
		try {
			PackageInfo pi = getActivity().getPackageManager().getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS);
			for (int i = 0; i < pi.requestedPermissions.length; i++) {
				if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
					granted.add(pi.requestedPermissions[i]);
				}
			}
		} catch (Exception e) {}
		return granted;
	}
}
