package com.vipapp.apk_share.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.adapter.AppsAdapter;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.data.StorageData;
import com.vipapp.apk_share.model.ApkInfoExtractor;

public class FilesAppFragment extends Fragment { 

	RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
	
	TextView text1, text2, text3;
	ProgressBar progressBar;

    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        View view = inflater.inflate(R.layout.main_files_fragment, container, false); 

		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		text1 = (TextView) view.findViewById(R.id.textAll2);
		text2 = (TextView) view.findViewById(R.id.textAll);
		text3 = (TextView) view.findViewById(R.id.textAll3);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        adapter = new AppsAdapter(getActivity(), new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());
        recyclerView.setAdapter(adapter);

		recyclerView.setNestedScrollingEnabled(true);
		
		float percentage = ((int)StorageData.getTotalInternalMemorySize() * 100.0f) / (int)StorageData.getAvailableInternalMemorySize();
		
		text1.setText(StorageData.formatSize(getActivity(), StorageData.getTotalInternalMemorySize()));
		text2.setText(StorageData.formatSize(getActivity(), StorageData.getAvailableInternalMemorySize()));
		text3.setText((int)percentage + "");
		
		progressBar.setMax((int)StorageData.getTotalInternalMemorySize());
		progressBar.setProgress((int)StorageData.getTotalInternalMemorySize() - (int)StorageData.getAvailableInternalMemorySize(), true);
		
		return view; 
	} 
}
