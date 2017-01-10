package xyz.enableit.shaketopup.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by dinislam on 11/7/16.
 */
public class SAppUtil {
    public static int getAppVersionCode(Context context) {
        int version = -1;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return version;
    }
}