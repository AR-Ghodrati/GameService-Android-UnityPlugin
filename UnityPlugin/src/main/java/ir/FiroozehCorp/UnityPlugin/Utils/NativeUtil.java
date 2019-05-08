package ir.FiroozehCorp.UnityPlugin.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.crypto.SecretKey;

public final class NativeUtil {

    private static final String PrefName = "FiroozehGameService";

    public static boolean IsUserLogin (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsUserLogin", false);
    }

    public static void SetUserLogin (Context context, boolean isLogin) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsUserLogin", isLogin);
        editor.apply();
    }

    private static String SetKey (Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();
        String key = GenerateRandomString(10);
        editor.putString("Key", key);
        editor.apply();
        return key;
    }

    private static String getKey (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Key", null);
    }


    public static void SetPlayToken (Context context, String Token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();

        String key = getKey(context);
        if (key == null)
            key = SetKey(context);

        try {
            SecretKey secretKey = EncryptionUtil.generateKey(key);
            byte[] data = EncryptionUtil.encryptString(Token, secretKey);
            editor.putString("Token", new String(data, "UTF-8"));
        } catch (Exception e) {

        } finally {
            editor.apply();
        }
    }

    public static String GetPlayToken (Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        if (token != null) {

            String key = getKey(context);
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
