package ir.FiroozehCorp.UnityPlugin.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.crypto.SecretKey;

public final class NativeUtil {

    private static final String PrefName = "FiroozehGameService";

    public static boolean IsUserLogin (Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsUserLogin", false);
    }

    public static void SetUserLogin (Activity activity, boolean isLogin) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsUserLogin", isLogin);
        editor.apply();
    }

    private static String SetKey (Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();
        String key = GenerateRandomString(10);
        editor.putString("Key", key);
        editor.apply();
        return key;
    }

    private static String getKey (Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Key", null);
    }


    public static void SetPlayToken (Activity activity, String Token) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();

        String key = getKey(activity);
        if (key == null)
            key = SetKey(activity);

        try {
            SecretKey secretKey = EncryptionUtil.generateKey(key);
            byte[] data = EncryptionUtil.encryptString(Token, secretKey);
            editor.putString("Token", new String(data, "UTF-8"));
        } catch (Exception e) {

        } finally {
            editor.apply();
        }
    }

    public static String GetPlayToken (Activity activity) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        if (token != null) {

            String key = getKey(activity);
            if (key != null) {
                try {
                    SecretKey secretKey = EncryptionUtil.generateKey(key);
                    return EncryptionUtil.decryptString(token.getBytes("UTF-8"), secretKey);
                } catch (Exception e) {

                }
            }
        }
        return null;
    }


    private static String GenerateRandomString (int length) {

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
