package ir.FiroozehCorp.UnityPlugin.Native.Handlers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.UnityPlugin.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.UnityPlugin.Native.Dialogs.LoginDialog;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonArrayCallbackListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.LoginListener;
import ir.FiroozehCorp.UnityPlugin.Native.Models.Game;
import ir.FiroozehCorp.UnityPlugin.Utils.ApiRequestUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.ConnectivityUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.DeviceInformationUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.FileUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.NativeUtil;


public final class UnityGameServiceNative implements LoginListener {

    private static final String TAG = "UnityGameServiceNative";
    private static UnityGameServiceNative Instance;
    public static boolean IsLogEnable = false;
    public static Long StartTime;
    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;
    private Game currentGame;


    // Play Token
    public static String PT;


    private Context context;
    public static Activity UnityActivity;

    public UnityGameServiceNative () {
        Instance = this;
    }

    public static UnityGameServiceNative GetInstance () {
        if (Instance == null) {
            VolleyLog.DEBUG = false; // Disable Volley Log
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

                                if (object.getBoolean("status")) {

                                    StartTime = System.currentTimeMillis();
                                    PT = object.getString("token");
                                    currentGame = new Gson().fromJson(object.getString("game"), Game.class);

                                    if (IsLogEnable)
                                        Log.d(TAG, "Success");

                                    InitCallback.OnCallback("Success");

                                }
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
        LoginDialog dialog = new LoginDialog(UnityActivity);
        dialog.setListener(this);
        dialog.show();
    }


    public void GetAchievements (final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.GetAchievement(UnityActivity, new JsonArrayCallbackListener() {
                @Override
                public void onResponse (JSONArray array) {
                    if (IsLogEnable)
                        Log.d(TAG, "GetAchievements : " + array.toString());

                    callback.OnCallback(array.toString());
                }

                @Override
                public void onError (String error) {
                    if (IsLogEnable)
                        Log.e(TAG, "GetAchievementsError : " + error);

                    callback.OnError(error);
                }
            });
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    //boolean Notification not Used
    public void UnlockAchievement (String ID, boolean Notification, final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            if (ID != null && !ID.isEmpty()) {
                ApiRequestUtil.EarnAchievement(UnityActivity, ID, new JsonObjectCallbackListener() {
                    @Override
                    public void onResponse (JSONObject object) {
                        if (IsLogEnable)
                            Log.d(TAG, "UnlockAchievement : " + object.toString());

                        try {
                            if (object.getBoolean("status")) {
                                JSONObject Obj = object.getJSONObject("new");
                                callback.OnCallback(Obj.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError (String error) {
                        if (IsLogEnable)
                            Log.e(TAG, "UnlockAchievementError : " + error);

                        callback.OnError(error);
                    }
                });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "InvalidInputs");

                callback.OnError("InvalidInputs");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void GetLeaderBoards (final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.GetLeaderBoards(UnityActivity, new JsonObjectCallbackListener() {
                @Override
                public void onResponse (JSONObject object) {
                    try {
                        String array = object.getJSONArray("leaderboard").toString();
                        if (IsLogEnable)
                            Log.d(TAG, "GetLeaderBoards : " + array);

                        callback.OnCallback(array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError (String error) {
                    if (IsLogEnable)
                        Log.e(TAG, "GetLeaderBoardsError : " + error);

                    callback.OnError(error);
                }
            });
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void GetLeaderBoardDetails (String ID, final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            if (ID != null && !ID.isEmpty()) {
                ApiRequestUtil.GetLeaderBoardData(UnityActivity, ID, new JsonObjectCallbackListener() {
                    @Override
                    public void onResponse (JSONObject object) {
                        if (IsLogEnable)
                            Log.d(TAG, "GetLeaderBoardDetails : " + object.toString());

                        callback.OnCallback(object.toString());
                    }

                    @Override
                    public void onError (String error) {
                        if (IsLogEnable)
                            Log.e(TAG, "GetLeaderBoardDetailsError : " + error);

                        callback.OnError(error);
                    }
                });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "InvalidInputs");

                callback.OnError("InvalidInputs");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void SubmitScore (String ID, int Score, boolean Notification, final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            if (Score > 0) {
                if (ID != null && !ID.isEmpty()) {
                    ApiRequestUtil.SubmitScore(UnityActivity, ID, Score, new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {
                                if (object.getBoolean("status")) {

                                    JSONObject Obj = object.getJSONObject("leaderboard");

                                    if (IsLogEnable)
                                        Log.d(TAG, "SubmitScore : " + Obj.toString());

                                    callback.OnCallback(Obj.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError (String error) {
                            if (IsLogEnable)
                                Log.e(TAG, "SubmitScoreError : " + error);

                            callback.OnError(error);
                        }
                    });
                } else {
                    if (IsLogEnable)
                        Log.e(TAG, "InvalidInputs");

                    callback.OnError("InvalidInputs");
                }
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "InvalidScore");

                callback.OnError("InvalidScore");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void SaveGame (
            String saveGameName
            , String saveGameDescription
            , String saveGameCover
            , String saveGameData
            , final IGameServiceCallback callback) {

        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            if ((saveGameName != null && !saveGameName.isEmpty())
                    && (saveGameData != null && !saveGameData.isEmpty())) {

                if (FileUtil.IsSaveFileSizeValid(saveGameData)) {
                    if (saveGameCover == null || FileUtil.IsSaveImgFileSizeValid(saveGameCover)) {

                        ApiRequestUtil.SaveGameData(
                                UnityActivity, saveGameName, saveGameDescription, saveGameCover, saveGameData
                                , new JsonObjectCallbackListener() {
                                    @Override
                                    public void onResponse (JSONObject object) {
                                        try {
                                            if (object.getBoolean("status")) {

                                                JSONObject Obj = object.getJSONObject("new");

                                                if (IsLogEnable)
                                                    Log.d(TAG, "SaveGame : " + Obj.toString());

                                                callback.OnCallback(Obj.toString());
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError (String error) {
                                        if (IsLogEnable)
                                            Log.e(TAG, "SaveGameError : " + error);

                                        callback.OnError(error);
                                    }
                                });
                    } else {
                        if (IsLogEnable)
                            Log.e(TAG, "InvalidSaveImgFileSize");

                        callback.OnError("InvalidSaveImgFileSize");
                    }
                } else {
                    if (IsLogEnable)
                        Log.e(TAG, "InvalidSaveFileSize");

                    callback.OnError("InvalidSaveFileSize");
                }

            } else {
                if (IsLogEnable)
                    Log.e(TAG, "InvalidInputs");

                callback.OnError("InvalidInputs");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }


    public void GetLastSave (final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.GetGameData(UnityActivity, new JsonObjectCallbackListener() {
                @Override
                public void onResponse (JSONObject object) {
                    if (IsLogEnable)
                        Log.d(TAG, "GetLastSave : " + object.toString());

                    callback.OnCallback(object.toString());
                }

                @Override
                public void onError (String error) {
                    if (IsLogEnable)
                        Log.e(TAG, "GetLastSaveError : " + error);

                    callback.OnError(error);
                }
            });
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void RemoveLastSave (final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            ApiRequestUtil.removeUserSaveFile(UnityActivity, new JsonObjectCallbackListener() {
                @Override
                public void onResponse (JSONObject object) {

                    try {
                        if (object.getBoolean("status")) {

                            if (IsLogEnable)
                                Log.d(TAG, "RemoveLastSave : Done");

                            callback.OnCallback("Done");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError (String error) {
                    if (IsLogEnable)
                        Log.e(TAG, "RemoveLastSaveError : " + error);

                    callback.OnError(error);
                }
            });
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
    }

    public void GetUserData (final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.getUserData(UnityActivity, new JsonObjectCallbackListener() {
                @Override
                public void onResponse (JSONObject object) {
                    try {
                        if (IsLogEnable)
                            Log.d(TAG, "GetUserData : " + object.getString("data"));

                        callback.OnCallback(object.getString("data"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError (String error) {
                    if (IsLogEnable)
                        Log.e(TAG, "GetUserDataError : " + error);

                    callback.OnError(error);
                }
            });
        } else {
            if (IsLogEnable)
                Log.e(TAG, "NetworkUnreachable");

            callback.OnError("NetworkUnreachable");
        }
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
