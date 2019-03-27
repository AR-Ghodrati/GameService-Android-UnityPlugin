package ir.FiroozehCorp.UnityPlugin.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public final class ConnectivityUtil {

    public static boolean isInternetAvailable () {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @SuppressLint ("MissingPermission")
    public static boolean isNetworkConnected (Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }

}
