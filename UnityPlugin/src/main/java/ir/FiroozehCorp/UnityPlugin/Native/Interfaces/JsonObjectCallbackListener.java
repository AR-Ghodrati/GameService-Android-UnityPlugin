package ir.FiroozehCorp.UnityPlugin.Native.Interfaces;

import org.json.JSONObject;

public interface JsonObjectCallbackListener {

    void onResponse (JSONObject object);

    void onError (String error);

}
