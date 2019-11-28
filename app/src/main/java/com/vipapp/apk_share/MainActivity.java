package com.vipapp.apk_share;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.adapter.AppsAdapter;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.fragment.MainFragment;
import com.vipapp.apk_share.model.ApkInfoExtractor;

public class MainActivity extends AppCompatActivity {
	
	RecyclerView.Adapter adapter;
	FragmentTransaction ft;
	Fragment frag;
	
	public boolean settings = false;

	/*static {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
	}*/
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Data.setTheme(this);
        setContentView(R.layout.main);
		
		if (savedInstanceState == null) {
            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
			setFragment(new MainFragment());
        }
    }
	@Override
	protected void onStart() {
		adapter = new AppsAdapter(this, new ApkInfoExtractor(this).GetAllInstalledApkInfo());
		setTitle(getString(R.string.app_name) + " - Apps (" + adapter.getItemCount() + ")");
		if(settings) {
			settings = false;
			this.recreate();
		}
		super.onStart();
	}
	
	public void setFragment(Fragment fr) {
		frag = fr;
        ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.contentMain, frag);
        ft.commit();
	}
	
	public void setSettings(Context ctx) {
		new MainActivity().settings = true;
		ctx.startActivity(new Intent(ctx, SettingsActivity.class));
	}
}
