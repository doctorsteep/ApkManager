package com.vipapp.apk_share.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vipapp.apk_share.R;
import org.vipapp.design.fm.util.FMTypeface;
import android.widget.LinearLayout;

public class InfoPermission {
	
	public static BottomSheetDialog dialog;

	public static void setPermissionMenu(final Context ctx, final String pkg, final String prm) {
		((Activity)ctx).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					final DataPermission dt = new DataPermission(ctx);
					LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					View view = inflater.inflate(R.layout.permission_menu, null);
					
					final RelativeLayout not = (RelativeLayout) view.findViewById(R.id.not);
					final RelativeLayout good = (RelativeLayout) view.findViewById(R.id.good);
					TextView name = (TextView) view.findViewById(R.id.title);
					TextView perm = (TextView) view.findViewById(R.id.perm);
					TextView desc = (TextView) view.findViewById(R.id.desc);
					ImageView icon = (ImageView) view.findViewById(R.id.icon);
					LinearLayout linI1 = (LinearLayout) view.findViewById(R.id.linI1);
					
					try {
						linI1.setClipToOutline(true);
					} catch (Exception e) {}
					
					name.setTypeface(FMTypeface.getTypeface(ctx, "bold"));
					desc.setTypeface(FMTypeface.getTypeface(ctx, "bold"));
					
					((Activity)ctx).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								not.setVisibility(View.GONE);
								good.setVisibility(View.VISIBLE);
							}
						});
					
					try {
						PackageManager packageManager = ctx.getPackageManager();
						PermissionInfo pinfo = packageManager.getPermissionInfo(prm, PackageManager.GET_META_DATA);
						name.setText(pinfo.loadLabel(packageManager).toString());
						perm.setText(prm);
						desc.setText(pinfo.loadDescription(packageManager).toString());
						icon.setImageDrawable(DataPermission.getPermissionDrawable(ctx, prm));
					} catch (Exception e) {
						((Activity)ctx).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									not.setVisibility(View.VISIBLE);
									good.setVisibility(View.GONE);
								}
							});
					}
						
					dialog = new BottomSheetDialog(ctx, R.style.BottomDialogTheme);
					dialog.setContentView(view);
					dialog.show();
				}
			});
	}
}
