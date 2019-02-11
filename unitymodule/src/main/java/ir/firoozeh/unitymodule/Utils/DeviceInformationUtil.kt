package ir.firoozeh.unitymodule.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.LocationManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.text.TextUtils
import android.view.Display
import ir.firoozeh.unitymodule.Models.SysInfo

/**
 * Created by Alireza Ghodrati on 16/10/2018 in
 * Time 10:53 AM for App ir.firoozeh.unitymodule.Utils
 */

object DeviceInformationUtil {


    /**
     * @return true if the current thread is the UI thread
     */
    val isUiThread: Boolean
        get() = Looper.getMainLooper().thread === Thread.currentThread()


    fun GetSystemInfo(a: Activity): SysInfo {
        val sysInfo = SysInfo()
        try {
            val pInfo = a.packageManager.getPackageInfo(
                    a.packageName, PackageManager.GET_META_DATA)
            sysInfo.PackageName = a.packageName
            sysInfo.VersionName = pInfo.versionName
            sysInfo.VersionCode = pInfo.versionCode

        } catch (e: PackageManager.NameNotFoundException) {
        }

        sysInfo.OSAPILevel = Build.VERSION.SDK
        sysInfo.Device = Build.DEVICE
        sysInfo.Model = Build.MODEL
        sysInfo.Product = Build.PRODUCT
        sysInfo.Manufacturer = Build.MANUFACTURER
        sysInfo.OtherTAGS = Build.TAGS
        sysInfo.ScreenWidth = a.window.windowManager.defaultDisplay
                .width
        sysInfo.ScreenHeight = a.window.windowManager.defaultDisplay
                .height

        sysInfo.SDCardState = Environment.getExternalStorageState()

        return sysInfo
    }

    fun isPositioningViaWifiEnabled(context: Context): Boolean {
        val cr = context.contentResolver
        val enabledProviders = Settings.Secure.getString(cr,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
        if (!TextUtils.isEmpty(enabledProviders)) {
            // not the fastest way to do that :)
            val providersList = TextUtils.split(enabledProviders, ",")
            for (provider in providersList) {
                if (LocationManager.NETWORK_PROVIDER == provider) {
                    return true
                }
            }
        }
        return false
    }

    fun isScreenOn(context: Context): Boolean {
        return context.getSystemService(Context.POWER_SERVICE) != null && (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
                .isScreenOn
    }

    /**
     * @param a
     * @return the size with size.x=width and size.y=height
     */
    fun getScreenSize(a: Activity): Point {
        return getScreenSize(a.windowManager.defaultDisplay)
    }

    @SuppressLint("NewApi")
    fun getScreenSize(d: Display): Point {
        val size = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            d.getSize(size)
        } else {
            size.x = d.width
            size.y = d.height
        }
        return size
    }


}
