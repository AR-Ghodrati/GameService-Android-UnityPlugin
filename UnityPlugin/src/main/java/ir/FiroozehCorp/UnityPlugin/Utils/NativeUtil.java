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

    public static String GenerateRandomString (int length) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
