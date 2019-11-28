package com.vipapp.apk_share.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import com.vipapp.apk_share.R;

public class DataPermission {

	private Context context;
	private static final String ANDROID = "android";
	
	public DataPermission(Context context) {
		this.context = context;
	}
	
	
	@Nullable
    public static Drawable getPermissionDrawable(Context ctx, String permission) {
        Drawable drawable = ctx.getDrawable(R.drawable.p_error);
        try {
			PackageManager packageManager = ctx.getPackageManager();
            PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
            PermissionGroupInfo groupInfo = packageManager.getPermissionGroupInfo(permissionInfo.group, 0);
            drawable = packageManager.getResourcesForApplication(ANDROID).getDrawable(groupInfo.icon);
        } catch (PackageManager.NameNotFoundException|Resources.NotFoundException e) {
        }
        return drawable;
    }
}
