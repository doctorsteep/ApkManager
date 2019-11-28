package com.vipapp.apk_share.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.ViewActivity;
import com.vipapp.apk_share.data.Data;
import com.vipapp.apk_share.model.ApkInfoExtractor;
import android.widget.RelativeLayout;
import org.vipapp.design.fm.util.FMTypeface;

public class Apk {
	
	public BottomSheetDialog dialog;
	
	public void setAppMenu(final Context ctx, final String pkg, final int data) {
		((Activity)ctx).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

					View view = inflater.inflate(R.layout.app_menu, null);

					final ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(ctx);
					final InterstitialAd mInterAd;
					final AdRequest adReg;

					ImageView iconI = (ImageView) view.findViewById(R.id.icon);
					ImageView imageSe = (ImageView) view.findViewById(R.id.ImageSearchInGooglePlay);
					TextView nameN = (TextView) view.findViewById(R.id.name);
					final TextView installedC = (TextView) view.findViewById(R.id.textInstalledGooglePlay);
					ImageView open = (ImageView) view.findViewById(R.id.imgOpen);
					ImageView remove = (ImageView) view.findViewById(R.id.imgDelete);
					ImageView share = (ImageView) view.findViewById(R.id.imgShare);
					ImageView settings = (ImageView) view.findViewById(R.id.imgSettings);
					final ImageView details = (ImageView) view.findViewById(R.id.imgDetails);
					final RelativeLayout goodRel = (RelativeLayout) view.findViewById(R.id.good);
					final RelativeLayout notRel = (RelativeLayout) view.findViewById(R.id.notI);

					final String name = apkInfoExtractor.GetAppName(pkg);

					iconI.setImageDrawable(apkInfoExtractor.getAppIconByPackageName(pkg));
					nameN.setText(name);
					
					nameN.setTypeface(FMTypeface.getTypeface(ctx, "bold"));

					/*mInterAd = new InterstitialAd(ctx); 
					mInterAd.setAdUnitId(""); 
					adReg = new AdRequest.Builder().addTestDevice("").addTestDevice("").build(); 
					//mInterAd.loadAd(adReg);*/
					
					mInterAd = new InterstitialAd(ctx); 
					mInterAd.setAdUnitId(""); 
					adReg = new AdRequest.Builder().addTestDevice("").addTestDevice("").build(); 
					mInterAd.loadAd(adReg);

					try {
						if (Data.verifyInstallerId(ctx, pkg)) {
							installedC.setBackgroundResource(R.drawable.tab_s);
							installedC.setText(ctx.getString(R.string.installed_from_gp));
						} else {
							installedC.setBackgroundResource(R.drawable.tab_red);
							installedC.setText(ctx.getString(R.string.not_installed_from_gp));
						}
					} catch (Exception e) {}
					
					if(!apkInfoExtractor.isSystemApp(pkg)) {
						remove.setAlpha((float)0.4);
						remove.setClickable(false);
						remove.setEnabled(false);
					}
					
					((Activity)ctx).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if(data != 0) {
									details.setAlpha((float)0.4);
									details.setClickable(false);
									details.setEnabled(false);
								}
								
								if(Data.isAppInstalled(ctx, pkg)) {
									goodRel.setVisibility(View.VISIBLE);
									notRel.setVisibility(View.GONE);
								} else {
									goodRel.setVisibility(View.GONE);
									notRel.setVisibility(View.VISIBLE);
								}
							}
						});

					try {
						iconI.setClipToOutline(true);
					} catch(Exception e) {}

					open.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(pkg);
								if(intent != null){
									ctx.startActivity(intent);
								} else {
									Toast.makeText(ctx, name + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
								}
							}
						});
					remove.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								Data.uninstallAPK(ctx, pkg);
							}
						});
					settings.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								try {
									Data.settingsApp(ctx, pkg);
									dialog.dismiss();
								} catch (Exception e) {}
							}
						});
					share.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								if(mInterAd.isLoaded()) {
									mInterAd.show();
								} else {
									try {
										Data.shareApplication(ctx, pkg, name);
									} catch (Exception e) {
										Toast.makeText(ctx, "Error share apk" + " - " + e.getMessage(), Toast.LENGTH_SHORT).show();
									}
								}
							}
						});
					details.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								ctx.startActivity(new Intent(ctx, ViewActivity.class).putExtra("pkg", pkg));
								((Activity)ctx).overridePendingTransition(0, 0);
								dialog.dismiss();
							}
						});
					imageSe.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View p1) {
								Data.searchAppGooglePlay(ctx, pkg);
							}
						});

					mInterAd.setAdListener(new AdListener(){ 
							public void onAdClosed(){ 
								mInterAd.loadAd(adReg);
								try {
									Data.shareApplication(ctx, pkg, "base");
								} catch (Exception e) {
									Toast.makeText(ctx, "Error share apk" + " - " + e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							} 
						});

					dialog = new BottomSheetDialog(ctx, Data.setThemeBottomDialog(ctx));
					dialog.setContentView(view);
					dialog.show();
				}
			});
	}
	
	public void setApkSizeMenu(Context ctx, String pkg) {
		
	}
}
