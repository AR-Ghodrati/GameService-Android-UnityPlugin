package ir.FiroozehCorp.GameService.UnityPackage.Native.Handlers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.GameService.UnityPackage.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Dialogs.LoginDialog;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.JsonArrayCallbackListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.LoginListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Achievement;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Bucket;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Game;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.LeaderBoard;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Services.GSNotificationService;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.ApiRequestUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.ConnectivityUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.DeviceInformationUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.FileUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.NativeUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.NotificationUtil;


public final class UnityGameServiceNative implements LoginListener {

    public static final String TAG = "UnityGameServiceNative";
    private static UnityGameServiceNative Instance;
    public static boolean IsLogEnable = false;

    public static Long StartTime;
    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;
    private Game currentGame;

    // Notification Service
    private GSNotificationService gsNotificationService;
    private NotificationListener notificationListener;

    // Play Token
    public static String PT;
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
        UnityActivity = activity;
    }

    public void InitGameService (
            String clientId
            , String clientSecret
            , boolean isLogEnable
            , IGameServiceCallback callback
            , NotificationListener listener) {

        if (clientId != null && clientSecret != null
                && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.InitCallback = callback;
            this.notificationListener = listener;
            IsLogEnable = isLogEnable;


            Log.d(TAG, "IsLogEnable : " + isLogEnable);


            if (NativeUtil.IsUserLogin(UnityActivity))
                initGameService();
            else loginUser();

        } else {
            callback.OnError("InvalidInputs");
        }

    }

    private void initGameService () {

        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.UpdatePlayTokenAsync(UnityActivity
                    , clientId, clientSecret,
                    DeviceInformationUtil.GetSystemInfo(UnityActivity, true)
                    , new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {

                                if (object.getBoolean("status")) {

                                    StartTime = System.currentTimeMillis();
                                    PT = object.getString("token");
                                    currentGame = new Gson().fromJson(object.getString("game"), Game.class);

                                    BindNotificationService(notificationListener, currentGame.get_id());


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
        Log.d(TAG, "UserNotLogin , Login UserCalled");

        UnityActivity.getWindow().closeAllPanels();
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

    public void UnlockAchievement (String ID, final boolean Notification, final IGameServiceCallback callback) {
        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            if (ID != null && !ID.isEmpty()) {
                ApiRequestUtil.EarnAchievement(UnityActivity, ID, new JsonObjectCallbackListener() {
                    @Override
                    public void onResponse (JSONObject object) {
                        try {
                            if (object.getBoolean("status")) {

                                JSONObject Obj = object.getJSONObject("new");
                                Achievement achievement = new Gson().fromJson(Obj.toString(), Achievement.class);

                                if (IsLogEnable)
                                    Log.d(TAG, "UnlockAchievement : " + achievement.toString());

                                if (Notification)
                                    NotificationUtil.NotifyAchievement(UnityActivity, achievement);

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

    public void SubmitScore (String ID, final int Score, final boolean Notification, final IGameServiceCallback callback) {
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

                                    if (Notification) {
                                        LeaderBoard leaderBoard = new Gson().fromJson(Obj.toString(), LeaderBoard.class);
                                        NotificationUtil.NotifySubmitScore(UnityActivity, Score, leaderBoard);
                                    }

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

    public void GetAllBucketData (final String BucketID, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty()) {
            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

                ApiRequestUtil.GetAllBucketDataByID(UnityActivity, BucketID,
                        new JsonArrayCallbackListener() {
                            @Override
                            public void onResponse (JSONArray array) {
                                callback.OnCallback(array.toString());
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "GetAllBucketData : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void GetOneBucketData (final String BucketID, final String ID, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty() && ID != null && !ID.isEmpty()) {

            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
                ApiRequestUtil.GetOneBucketDataByID(UnityActivity, BucketID, ID,
                        new JsonObjectCallbackListener() {
                            @Override
                            public void onResponse (JSONObject object) {
                                Gson gson = new Gson();
                                Bucket bucket = gson.fromJson(object.toString(), Bucket.class);
                                callback.OnCallback(gson.toJson(bucket));
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "GetOneBucketData : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void UpdateOneBucketData (final String BucketID, final String ID, final String BucketJSON, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty() && ID != null && !ID.isEmpty() && BucketJSON != null && !BucketJSON.isEmpty()) {

            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
                ApiRequestUtil.UpdateOneBucketDataByID(UnityActivity, BucketID, ID, BucketJSON,
                        new JsonObjectCallbackListener() {
                            @Override
                            public void onResponse (JSONObject object) {
                                Gson gson = new Gson();
                                Bucket bucket = gson.fromJson(object.toString(), Bucket.class);
                                callback.OnCallback(gson.toJson(bucket));
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "UpdateOneBucketData : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void AddNewBucketData (final String BucketID, final String BucketJSON, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty() && BucketJSON != null && !BucketJSON.isEmpty()) {
            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
                ApiRequestUtil.AddOneBucketByID(UnityActivity, BucketID, BucketJSON,
                        new JsonObjectCallbackListener() {
                            @Override
                            public void onResponse (JSONObject object) {
                                Gson gson = new Gson();
                                Bucket bucket = gson.fromJson(object.toString(), Bucket.class);
                                callback.OnCallback(gson.toJson(bucket));
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "AddNewBucketData : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void DeleteOneBucket (final String BucketID, final String ID, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty() && ID != null && !ID.isEmpty()) {
            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
                ApiRequestUtil.DeleteOneBucketByID(UnityActivity, BucketID, ID,
                        new JsonObjectCallbackListener() {
                            @Override
                            public void onResponse (JSONObject object) {
                                try {
                                    callback.OnCallback(String.valueOf(object.getBoolean("status")));
                                } catch (JSONException e) {
                                    callback.OnCallback("false");
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "DeleteOneBucket : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void DeleteAllBucketData (final String BucketID, final IGameServiceCallback callback) {
        if (BucketID != null && !BucketID.isEmpty()) {
            if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
                ApiRequestUtil.DeleteAllBucketByID(UnityActivity, BucketID,
                        new JsonObjectCallbackListener() {
                            @Override
                            public void onResponse (JSONObject object) {
                                try {
                                    callback.OnCallback(String.valueOf(object.getBoolean("status")));
                                } catch (JSONException e) {
                                    callback.OnCallback("false");
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError (String error) {

                                if (IsLogEnable)
                                    Log.e(TAG, "DeleteAllBucketData : " + error);

                                callback.OnError(error);
                            }
                        });
            } else {
                if (IsLogEnable)
                    Log.e(TAG, "NetworkUnreachable");

                callback.OnError("NetworkUnreachable");
            }
        } else {
            if (IsLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
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

    private void BindNotificationService (final NotificationListener listener, final String GameID) {
        ServiceConnection gsNotificationConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected (ComponentName name, IBinder service) {
                GSNotificationService.LocalBinder binder = (GSNotificationService.LocalBinder) service;
                gsNotificationService = binder.get();

                gsNotificationService.StartWSClient(GameID, NativeUtil.GetJWT(UnityActivity), new NotificationListener() {
                    @Override
                    public void onData (String JsonData) {
                        if (listener != null) listener.onData(JsonData);
                        else {
                            if (IsLogEnable)
                                Log.e(TAG, "JsonData Listener Is Null");
                        }
                    }
                });

                if (IsLogEnable)
                    Log.d(TAG, "Notification Service Connected!");
            }

            @Override
            public void onServiceDisconnected (ComponentName name) {
                if (IsLogEnable)
                    Log.d(TAG, "Notification Service Disconnected!");

            }
        };

        UnityActivity.startService(new Intent(UnityActivity, GSNotificationService.class));
        UnityActivity.bindService(new Intent(UnityActivity, GSNotificationService.class)
                , gsNotificationConnection, Context.BIND_AUTO_CREATE);

    }
}

