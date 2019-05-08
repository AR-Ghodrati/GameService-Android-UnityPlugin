package ir.FiroozehCorp.UnityPlugin.Native.Handlers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.UnityPlugin.Native.Dialogs.LoginDialog;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.LoginListener;
import ir.FiroozehCorp.UnityPlugin.Native.Models.Game;
import ir.FiroozehCorp.UnityPlugin.Utils.ApiRequestUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.ConnectivityUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.DeviceInformationUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.NativeUtil;


public final class UnityGameServiceNative implements LoginListener {

    private static final String TAG = "UnityGameServiceNative";
    private static UnityGameServiceNative Instance;
    public static boolean IsLogEnable = false;
    public static Long StartTime;
    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;
    private Game currentGame;


    private Context context;
    private Activity UnityActivity;

    public UnityGameServiceNative () {
        Instance = this;
    }

    public static UnityGameServiceNative GetInstance () {
        if (Instance == null) {
            Instance = new UnityGameServiceNative();
        }
        return Instance;
    }

    public void SetUnityContext (Activity activity) {
        this.context = activity.getApplicationContext();
        this.UnityActivity = activity;
    }

    public void InitGameService (
            String clientId
            , String clientSecret
            , boolean isLogEnable
            , IGameServiceCallback callback) {

        if (clientId != null && clientSecret != null
                && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.InitCallback = callback;
            IsLogEnable = isLogEnable;

            if (NativeUtil.IsUserLogin(UnityActivity))
                initGameService();
            else loginUser();

        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }

    }

    private void initGameService () {

        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.UpdatePlayTokenAsync(UnityActivity
                    , clientId, clientSecret,
                    DeviceInformationUtil.GetSystemInfo(UnityActivity)
                    , new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {

                                StartTime = System.currentTimeMillis();
                                NativeUtil.SetPlayToken(UnityActivity, object.getString("token"));
                                currentGame = new Gson().fromJson(object.getString("game"), Game.class);

                                if (IsLogEnable)
                                    Log.d(TAG, "Success");

                                InitCallback.OnCallback("Success");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError (String error) {
                            if (IsLogEnable)
                                Log.e(TAG, "GameServiceException , " + error);

                            InitCallback.OnError("GameServiceException");
                        }
                    });

        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            InitCallback.OnError("NetworkUnreachable");
        }
    }

    private void loginUser () {
        LoginDialog dialog = new LoginDialog(context);
        dialog.setListener(this);
        dialog.show();
    }


    @Override
    public void onFinish () {
        initGameService();
    }

    @Override
    public void onError (String error) {
        InitCallback.OnError(error);
    }

    @Override
    public void onDismiss () {

        if (IsLogEnable)
            Log.e(TAG, "LoginDismissed");


        InitCallback.OnError("LoginDismissed");
    }
}
