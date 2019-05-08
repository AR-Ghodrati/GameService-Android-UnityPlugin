package ir.FiroozehCorp.UnityPlugin.Utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ir.FiroozehCorp.UnityPlugin.App.Interfaces.UpdateUtilListener;

public final class UpdateUtil {

    public static void CheckUpdate (
            final boolean CheckOptionalUpdate
            , final int CurrentVerCode
            , final UpdateUtilListener listener) {

        try {
            JSONObject jsonObject = new JSONObject(ReadURL());
            try {
                if (jsonObject.getBoolean("status")) {

                    int NewVerCode = jsonObject.getJSONObject("data").getInt("ver_code");
                    boolean MustUpdate = jsonObject.getJSONObject("data").getBoolean("must_update");
                    String ChangeLog = jsonObject.getJSONObject("data").getString("change_log");

                    if (MustUpdate) {
                        if (NewVerCode > CurrentVerCode)
                            listener.onUpdateAvailable(ChangeLog, true);
                        else listener.onHaveLastVersion();
                    } else {
                        if (CheckOptionalUpdate) {
                            if (NewVerCode > CurrentVerCode)
                                listener.onUpdateAvailable(ChangeLog, false);
                            else listener.onHaveLastVersion();
                        } else listener.onForceInit();
                    }
                } else listener.onForceInit();
            } catch (JSONException e) {
                listener.onForceInit();
                e.printStackTrace();
            }


        } catch (Exception e) {
            listener.onForceInit();
            e.printStackTrace();
        }

    }

    private static String ReadURL () throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://gameservice.liara.run/download/app/update");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null)
            result.append(line);

        rd.close();
        return result.toString();
    }

}
