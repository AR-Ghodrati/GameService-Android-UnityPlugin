package ir.FiroozehCorp.UnityPlugin.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public final class NativeUtil {

    public static boolean IsUserLogin (Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("FiroozehGameService", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsUserLogin", false);
    }

    public static void SetUserLogin (Activity activity, boolean isLogin) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("FiroozehGameService", Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsUserLogin", isLogin);
        editor.apply();
    }
}
