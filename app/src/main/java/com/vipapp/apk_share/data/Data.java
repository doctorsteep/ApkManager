package com.vipapp.apk_share.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import com.vipapp.apk_share.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.vipapp.design.fm.util.*;

public class Data {
	
	public static void setTheme(Context act) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(act);
		setCustomFont(act, "SERIF", "fonts/main.ttf");
		
		String themeValue = sp.getString("themeKey", "");
		switch (themeValue) {
			case "1":
				int nightModeFlags = act.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						act.setTheme(R.style.AppTheme_Dark);
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						act.setTheme(R.style.AppTheme);
						break;
				}
				break;
			case "2":
				act.setTheme(R.style.AppTheme);
				break;
			case "3":
				act.setTheme(R.style.AppTheme_Dark);
				break;
		}
	}
	
	public static void setThemeDefault(Context act) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(act);
		setCustomFont(act, "SERIF", "fonts/main.ttf");

		String themeValue = sp.getString("themeKey", "");
		switch (themeValue) {
			case "1":
				int nightModeFlags = act.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						act.setTheme(R.style.AppTheme_Default_Dark);
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						act.setTheme(R.style.AppTheme_Default);
						break;
				}
				break;
			case "2":
				act.setTheme(R.style.AppTheme_Default);
				break;
			case "3":
				act.setTheme(R.style.AppTheme_Default_Dark);
				break;
		}
	}
	
	public static int setThemeBottomDialog(Context act) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(act);
		
		int theme = R.style.BottomDialogTheme;
		String themeValue = sp.getString("themeKey", "");
		switch (themeValue) {
			case "1":
				int nightModeFlags = act.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						theme = R.style.BottomDialogThemeDark;
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						theme = R.style.BottomDialogTheme;
						break;
				}
				break;
			case "2":
				theme = R.style.BottomDialogTheme;
				break;
			case "3":
				theme = R.style.BottomDialogThemeDark;
				break;
		}
		
		return theme;
	}

	public static void setCustomFont(Context _con, String _defaultFontNameToOverride, String _customFontFileNameInAssets) {
        try {
            //final Typeface customFontTypeface = Typeface.createFromAsset(_con.getAssets(), _customFontFileNameInAssets);
			final Typeface customFontTypeface = FMTypeface.getTypeface(_con, "regular");
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(_defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            //Log.e("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
	}
	
	public static void changeToolbarFont(Toolbar toolbar, Activity context) {
		for (int i = 0; i < toolbar.getChildCount(); i++) {
			View view = toolbar.getChildAt(i);
			if (view instanceof TextView) {
				TextView tv = (TextView) view;
				if (tv.getText().equals(toolbar.getTitle())) {
					applyFont(tv, context);
					break;
				}
			}
		}
	}

	public static void applyFont(TextView tv, Activity context) {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/main.ttf"));
	}
	
	
	public static void shareApplication(Context ctx, String pkg, String name) throws Exception{
		ProgressDialog pd = new ProgressDialog(ctx);
		pd.setMessage("Loading...");
		pd.setCancelable(false);
		
		ApplicationInfo app = null;
		PackageManager packageManager = ctx.getPackageManager();
		try {
			app = packageManager.getApplicationInfo(pkg, 0);
		} catch (PackageManager.NameNotFoundException e) {}

		String filePath = app.sourceDir;

		Intent intent = new Intent(Intent.ACTION_SEND);

		intent.setType("*/*");

		File originalApk = new File(filePath);

		pd.show();
		
		try {
			File tempFile = new File(ctx.getExternalCacheDir() + "/ApkShare");
			if (!tempFile.isDirectory())
				if (!tempFile.mkdirs())
					return;
			tempFile = new File(tempFile.getPath() + "/" + "base" + ".apk");
			if (!tempFile.exists()) {
				if (!tempFile.createNewFile()) {
					return;
				}
			}
			Uri apk = FileProvider.getUriForFile(ctx, "com.vipapp.apk_share.provider", tempFile);
			
			//Copy file to new location
			InputStream in = new FileInputStream(originalApk);
			OutputStream out = new FileOutputStream(tempFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");
			intent.putExtra(Intent.EXTRA_STREAM, apk);
			ctx.startActivity(Intent.createChooser(intent, ctx.getString(R.string.share) + " - \"" + name + "\""));
			pd.dismiss();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verifyInstallerId(Context context, String pkg) throws Exception {
		List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));
		final String installer = context.getPackageManager().getInstallerPackageName(pkg);
		return installer != null && validInstallers.contains(installer);
	}
	
	public static boolean isAppInstalled(Context ctx, String pkg) {
		try {
			ctx.getPackageManager().getApplicationInfo(pkg, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	
	public static void installAPK(Context ctx, File apkFile) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}
	public static void uninstallAPK(Context ctx, String apkPackageName) {
		Intent intent = new Intent("android.intent.action.DELETE");
		intent.setData(Uri.parse("package:" + apkPackageName));
		ctx.startActivity(intent);
	}
	
	public static void searchAppGooglePlay(Context ctx, String appPackageName) {
		try {
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (ActivityNotFoundException anfe) {
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}
	
	public static String modifyDate(String incomeDate, String dateModify) {
		String dateAsString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateModify);
            Date d = sdf.parse(incomeDate);
            SimpleDateFormat sdfOut = new SimpleDateFormat("dd MMM yyyy");
           	dateAsString = sdfOut.format(d);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return dateAsString;
    }
	
	public static void settingsApp(Context ctx, String pkg) throws Exception {
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME"); 
		
		Intent inten = new Intent();
		inten.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", pkg, null);
		inten.setData(uri);
		ctx.startActivity(inten);
	}
	
	public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5);
        return noOfColumns;
    }
	
	public static int getNumberOfColumns(Context ctx) {
        View view = View.inflate(ctx, R.layout.list_app_table, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = view.getMeasuredWidth();
        int count = ctx.getResources().getDisplayMetrics().widthPixels / width;
        int remaining = ctx.getResources().getDisplayMetrics().widthPixels - width * count;
        if (remaining > width - 15)
            count++;
        return count;
    }
	
	public static void setClipboard(Context context, String text) {
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
			clipboard.setPrimaryClip(clip);
		}
	}
	
	
	public static long getSpeedAnimation(Context ctx) {
		return 300;
	}
}
