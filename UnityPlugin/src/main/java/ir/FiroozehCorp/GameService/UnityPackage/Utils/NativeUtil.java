package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.crypto.SecretKey;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.OBB;

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
        String key = GenerateRandomString(32);
        editor.putString("Key", key);
        editor.apply();
        return key;
    }

    private static String getKey (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Key", null);
    }


    public static void SetJWT (Context context, String Token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();

        String key = getKey(context);
        if (key == null)
            key = SetKey(context);

        try {
            SecretKey secretKey = EncryptionUtil.generateKey(key);
            editor.putString("JWT", EncryptionUtil.encryptString(Token, secretKey));
            editor.apply();

        } catch (Exception ignored) {
        }
    }

    public static String GetJWT (Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("JWT", null);

        if (token != null) {

            String key = getKey(context);
            if (key != null) {
                try {
                    SecretKey secretKey = EncryptionUtil.generateKey(key);
                    return EncryptionUtil.decryptString(token, secretKey);
                } catch (Exception e) {

                }
            }
        }
        return null;
    }

    public static void setOBBMetaData (Context context, String tag, Long Size) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE).edit();

        OBB obb = new OBB();
        obb.setName(tag);
        obb.setSize(Size);

        editor.putString("OBBMetaData", new Gson().toJson(obb));
        editor.apply();
    }

    public static OBB GetOBBMetaData (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        String meta = sharedPreferences.getString("OBBMetaData", null);

        if (meta != null)
            return new Gson().fromJson(meta, OBB.class);
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
