package com.vipapp.apk_share.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.adapter.AppsAdapter;
import com.vipapp.apk_share.data.ThreadLoaded;
import com.vipapp.apk_share.model.ApkInfoExtractor;

public class InstalledAppFragment extends Fragment { 

	public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
        View view = inflater.inflate(R.layout.list_recycler, container, false); 
		
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

		recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

		adapter = new AppsAdapter(getActivity(), new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());
		recyclerView.setAdapter(adapter);
		
		/*new ThreadLoaded(new Runnable() {
				@Override
				public void run() {
					adapter = new AppsAdapter(getActivity(), new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());
				}
			}, new Runnable() {
				@Override
				public void run() {
					recyclerView.setAdapter(adapter);
				}
			});*/
		
		//adapter.setHasStableIds(true);
		
		recyclerView.setHasFixedSize(true);
		recyclerView.setItemViewCacheSize(8);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

		recyclerView.setNestedScrollingEnabled(true);
		
		return view; 
	}
}
