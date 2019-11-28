package com.vipapp.apk_share.data;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import com.vipapp.apk_share.R;
import java.io.File;

public class StorageData {

	private static long ERROR = 0;
	
	public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
			android.os.Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    public static String formatSize(Context ctx, long size) {
        String suffix = null;
		
        if (size >= 1024) {
            suffix = " " + ctx.getString(R.string.kb);
            size /= 1024;
            if (size >= 1024) {
                suffix = " " + ctx.getString(R.string.mb);
                size /= 1024;
				if (size >= 1024) {
					suffix = " " + ctx.getString(R.string.gb);
					size /= 1024;
				}
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}
