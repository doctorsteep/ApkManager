package com.vipapp.apk_share.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import com.vipapp.apk_share.R;
import com.vipapp.apk_share.data.Data;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



public class ApkInfoExtractor {

    Context context1;
	SharedPreferences sp;
	boolean appSys;
	public String nameApp;

    public ApkInfoExtractor(Context context2){
        context1 = context2;
		sp = PreferenceManager.getDefaultSharedPreferences(context1);
		appSys = sp.getBoolean("sysAppKey", false);
    }

    public List<String> GetAllInstalledApkInfo(){
        List<String> ApkPackageName = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent, 0);

        for(ResolveInfo resolveInfo : resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;

			if(appSys) {
				ApkPackageName.add(activityInfo.applicationInfo.packageName);
			} else {
				if(!isSystemPackage(resolveInfo)){
					ApkPackageName.add(activityInfo.applicationInfo.packageName);
					
				}
			}
			nameApp = new ApkInfoExtractor(context1).GetAppName(activityInfo.applicationInfo.packageName);
        }
        return ApkPackageName;
    }

    public boolean isSystemPackage(ResolveInfo resolveInfo){
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName){
        Drawable drawable;

        try{
            drawable = context1.getPackageManager().getApplicationIcon(ApkTempPackageName);
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(context1, R.drawable.ic_launcher);
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName){
        String Name = "No name";
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context1.getPackageManager();

        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);
            if(applicationInfo!=null){
                Name = (String) packageManager.getApplicationLabel(applicationInfo);
            }
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Name;
    }
	
	public String GetAppVersion(String ApkPackageName){
        String Version = "0.0";
		try {
			PackageInfo pInfo = context1.getPackageManager().getPackageInfo(ApkPackageName, 0);
			Version = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
        
        return Version;
    }
	
	public String GetAppVersionCode(String ApkPackageName){
        String Version = "0";
		try {
			PackageInfo pInfo = context1.getPackageManager().getPackageInfo(ApkPackageName, 0);
			Version = pInfo.versionCode + "";
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

        return Version;
    }
	
	public String GetAppLastUpdateTime(String ApkPackageName){
        String Update = "00.00.0000";
		try {
			PackageInfo pInfo = context1.getPackageManager().getPackageInfo(ApkPackageName, 0);
			Date updateTime = new Date(pInfo.lastUpdateTime);
			Update = Data.modifyDate(updateTime.toString(), "yyyy-MM-dd");
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

        return Update;
    }
	
	public String GetAppInstallTime(String ApkPackageName){
        String Install = "00.00.0000";
		try {
			PackageInfo pInfo = context1.getPackageManager().getPackageInfo(ApkPackageName, 0);
			Date installTime = new Date(pInfo.firstInstallTime);
			Install = Data.modifyDate(installTime.toString(), "yyyy-MM-dd");
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

        return Install;
    }
	
	public String GetAppSize(String ApkPackageName){
        String Install = "0 " + context1.getResources().getString(R.string.mb);
		try {
			float value = new File(context1.getPackageManager().getApplicationInfo(ApkPackageName, 0).publicSourceDir).length() / 1048576F;

			if (value <= 0) {
				value = 1;
			}

			Install = String.format("%.2f", value) + " " + context1.getResources().getString(R.string.mb);
		} catch(Exception e) {}

        return Install;
    }
	
	public String getAppDeveloper(String pkg) {
		String[] split = pkg.split("\\.");
		String devName = split[1];
		String cap = devName.substring(0, 1).toUpperCase() + devName.substring(1);
		String value = cap.replaceAll("_", " ");

		return value;
	}
	
	public boolean isSystemApp(String _package) {
        try {
            ApplicationInfo ai = context1.getPackageManager().getApplicationInfo(_package, 0);
            int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
            return (ai.flags & mask) == 0;
        }catch (PackageManager.NameNotFoundException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }
}
