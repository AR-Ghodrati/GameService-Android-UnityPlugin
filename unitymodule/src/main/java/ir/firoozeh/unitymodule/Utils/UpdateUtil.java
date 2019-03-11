package ir.firoozeh.unitymodule.Utils;

import android.app.Activity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ir.firoozeh.unitymodule.Interfaces.UpdateUtilListener;

public final class UpdateUtil {

    public static void CheckUpdate (
            Activity activity
            , final boolean CheckOptionalUpdate
            , final int CurrentVerCode
            , final UpdateUtilListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://gameservice.liara.run/download/app/update",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {

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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {

            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        request.setShouldCache(false);
        Volley.newRequestQueue(activity).add(request);

    }

}
