package ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces;

import org.json.JSONArray;

public interface JsonArrayCallbackListener {

    void onResponse (JSONArray array);

    void onError (String error);
}
