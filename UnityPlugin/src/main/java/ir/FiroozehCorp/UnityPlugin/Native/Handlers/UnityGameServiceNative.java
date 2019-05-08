package ir.FiroozehCorp.UnityPlugin.Native.Handlers;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.UnityPlugin.Native.Dialogs.LoginDialog;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.LoginListener;
import ir.FiroozehCorp.UnityPlugin.Utils.ApiRequestUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.ConnectivityUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.DeviceInformationUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.NativeUtil;


public final class UnityGameServiceNative implements LoginListener {

    private static UnityGameServiceNative Instance;
    public static boolean IsLogEnable = false;
    public static Long StartTime;
    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;


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

    public void InitGameService (String clientId, String clientSecret
            , IGameServiceCallback callback) {

        if (clientId != null && clientSecret != null
                && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.InitCallback = callback;

            if (NativeUtil.IsUserLogin(UnityActivity))
                initGameService();
            else loginUser();

        } else callback.OnError("InvalidInputs");

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

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError (String error) {

                        }
                    });

        } else InitCallback.OnError("NetworkUnreachable");
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
        InitCallback.OnError("LoginDismissed");
    }
}
