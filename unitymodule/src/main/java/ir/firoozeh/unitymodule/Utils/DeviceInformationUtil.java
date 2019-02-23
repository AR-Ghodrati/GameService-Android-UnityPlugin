package ir.firoozeh.unitymodule.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;

import ir.firoozeh.unitymodule.Models.SysInfo;

/**
 * Created by Alireza Ghodrati on 16/10/2018 in
 * Time 10:53 AM for App ir.firoozeh.unitymodule.Utils
 */

public final class DeviceInformationUtil {


    public static SysInfo GetSystemInfo (Activity a) {
        SysInfo sysInfo = new SysInfo();
        try {
            PackageInfo pInfo = a.getPackageManager().getPackageInfo(
                    a.getPackageName(), PackageManager.GET_META_DATA);
            sysInfo.setPackageName(a.getPackageName());
            sysInfo.setVersionName(pInfo.versionName);
            sysInfo.setVersionCode(pInfo.versionCode);

            sysInfo.setGameOrientation(a.getResources().getConfiguration().orientation);

        } catch (PackageManager.NameNotFoundException e) {
        }
        sysInfo.setOSAPILevel(Build.VERSION.SDK);
        sysInfo.setDevice(Build.DEVICE);
        sysInfo.setModel(Build.MODEL);
        sysInfo.setProduct(Build.PRODUCT);
        sysInfo.setManufacturer(Build.MANUFACTURER);
        sysInfo.setOtherTAGS(Build.TAGS);
        sysInfo.setScreenWidth(a.getWindow().getWindowManager().getDefaultDisplay()
                .getWidth());
        sysInfo.setScreenHeight(a.getWindow().getWindowManager().getDefaultDisplay()
                .getHeight());

        sysInfo.setSDCardState(Environment.getExternalStorageState());

        return sysInfo;
    }

    public static boolean isPositioningViaWifiEnabled (Context context) {
        ContentResolver cr = context.getContentResolver();
        String enabledProviders = Settings.Secure.getString(cr,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!TextUtils.isEmpty(enabledProviders)) {
            // not the fastest way to do that :)
            String[] providersList = TextUtils.split(enabledProviders, ",");
            for (String provider : providersList) {
                if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isScreenOn (Context context) {
        return context.getSystemService(Context.POWER_SERVICE) != null && ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
                .isScreenOn();
    }


    /**
     * @return true if the current thread is the UI thread
     */
    public static boolean isUiThread () {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * @param a
     * @return the size with size.x=width and size.y=height
     */
    public static Point getScreenSize (Activity a) {
        return getScreenSize(a.getWindowManager().getDefaultDisplay());
    }

    @SuppressLint ("NewApi")
    public static Point getScreenSize (Display d) {
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            d.getSize(size);
        } else {
            size.x = d.getWidth();
            size.y = d.getHeight();
        }
        return size;
    }


}
