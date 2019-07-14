package ir.FiroozehCorp.GameService.UnityPackage.App.Handlers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import ir.FiroozehCorp.GameService.IAsyncGameServiceCallback;
import ir.FiroozehCorp.GameService.IGameServiceInterface;
import ir.FiroozehCorp.GameService.UnityPackage.App.Interfaces.InstallDialogListener;
import ir.FiroozehCorp.GameService.UnityPackage.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Services.GSNotificationService;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.DeviceInformationUtil;

/**
 * Created by Alireza Ghodrati on 04/10/2018 in
 * Time 02:36 PM for App ir.firoozeh.unitymodule
 */

public final class UnityGameService implements InstallDialogListener {

    private static UnityGameService Instance;
    private final String TAG = getClass().getName();
    private IGameServiceInterface gameServiceInterface;
    private GameService gameService;
    private Context context;
    private Activity UnityActivity;
    private boolean isLogEnable;

    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;


    // Notification Service
    private GSNotificationService gsNotificationService;
    private NotificationListener notificationListener;

    public UnityGameService () {
        Instance = this;
    }

    public static UnityGameService GetInstance () {
        if (Instance == null) {
            Instance = new UnityGameService();
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
            , IGameServiceCallback callback
            , NotificationListener listener) {

        if (clientId != null && clientSecret != null
                && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.InitCallback = callback;
            this.isLogEnable = isLogEnable;
            this.notificationListener = listener;

            initGameService(InitCallback);

        } else {
            if (isLogEnable)
                Log.e(TAG, "InvalidInputs");

            callback.OnError("InvalidInputs");
        }
    }

    public void DisposeService () {
        releaseGameService();
    }

    private void releaseGameService () {
        this.context.unbindService(gameService);
        gameService = null;

        if (isLogEnable)
            Log.d(TAG, "releaseGameService(): unbound.");
    }

    private void initGameService (IGameServiceCallback callback) {

        try {
            if (isPackageInstalled(context.getPackageManager())) {

                if (isLogEnable)
                    Log.e(TAG, "initGameService()");

                gameService = new GameService();

                Intent i = new Intent(
                        "ir.FiroozehCorp.GameService.Services.GameService.BIND");
                i.setPackage("ir.FiroozehCorp.GameService");

                boolean ret = context.bindService(i, gameService, Context.BIND_AUTO_CREATE);
                if (!ret) {
                    if (isLogEnable)
                        Log.e(TAG, "GameServiceNotBounded");

                    callback.OnError("GameServiceNotBounded");
                }
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "initGameService() Exception:" + e.toString());

            callback.OnError("GameServiceException");
        }
    }

    public void GetAchievements (final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetAchievementError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestAchievement(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void GetSDKVersion (final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetSDKVersionError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestVersion(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void GetLeaderBoards (final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetLeaderBoardsError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestLeaderBoards(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void GetLeaderBoardDetails (String LeaderBoardID, final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetLeaderBoardDataError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestLeaderBoardData(LeaderBoardID, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {

            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowLeaderBoardUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {

                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "ShowLeaderBoardUIError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowLeaderBoardUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowAchievementUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "ShowAchievementUIError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowAchievementUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void ShowSurveyUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "ShowSurveyUIError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowSurveyUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void ShowGamePageUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "ShowGamePageUIError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowGamePageUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void UnlockAchievement (String AchievementID, boolean HaveNotification, final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "UnlockAchievementError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestUnlockAchievement(AchievementID, HaveNotification, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());


            callback.OnError("GameServiceFatalException");
        }
    }

    public void SubmitScore (String Id, int Score, boolean HaveNotification, final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "SubmitScore: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestSubmitScore(Id, Score, HaveNotification, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());


            callback.OnError("GameServiceFatalException");
        }
    }

    public void GetLastSave (final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");


                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetLastSaveError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestGetData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());


            callback.OnError("GameServiceFatalException");
        }
    }

    public void SaveGame (String Name, String Description, String Cover, String SaveData, final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "SaveDataError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestSaveData(Name, Description, Cover, SaveData, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void RemoveLastSave (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "RemoveLastSaveError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestRemoveSaveData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void GetUserData (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");

                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "GetUserDataError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestGetUserData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    public void DownloadObbDataFile (String ObbDataTAG, final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                if (isLogEnable)
                    Log.e(TAG, "UnreachableService");


                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        if (isLogEnable)
                            Log.e(TAG, "DownloadObbDataFileError: " + Error);

                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestDownloadObbDataFile(ObbDataTAG, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "GameServiceFatalException,e : " + e.toString());

            callback.OnError("GameServiceFatalException");
        }
    }

    /* Remove This Feature
    public void DownloadBundleDataFile (String BundleDataTAG, final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        callback.OnCallback(Result);
                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestDownloadBundleDataFile(BundleDataTAG, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }
    */


    private boolean isPackageInstalled (PackageManager packageManager) {
        try {
            packageManager.getPackageInfo("ir.FiroozehCorp.GameService", 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getGameName (PackageManager packageManager, String packageName) {
        try {
            return packageManager.getPackageInfo(packageName, 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public void onDismiss () {
        InitCallback.OnError("GameServiceInstallDialogDismiss");
    }

    private void BindNotificationService (final NotificationListener listener, final String GameID, final String JWT) {
        ServiceConnection gsNotificationConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected (ComponentName name, IBinder service) {
                GSNotificationService.LocalBinder binder = (GSNotificationService.LocalBinder) service;
                gsNotificationService = binder.get();

                gsNotificationService.StartWSClient(GameID, JWT, new NotificationListener() {
                    @Override
                    public void onData (String JsonData) {
                        if (listener != null) listener.onData(JsonData);
                        else {
                            if (isLogEnable)
                                Log.e(TAG, "JsonData Listener Is Null");
                        }
                    }
                });

                if (isLogEnable)
                    Log.d(TAG, "Notification Service Connected!");
            }

            @Override
            public void onServiceDisconnected (ComponentName name) {
                if (isLogEnable)
                    Log.d(TAG, "Notification Service Disconnected!");

            }
        };

        boolean ret = UnityActivity.bindService(new Intent(UnityActivity, GSNotificationService.class)
                , gsNotificationConnection, Context.BIND_AUTO_CREATE);

        if (!ret) {
            if (isLogEnable)
                Log.e(TAG, "GSNotificationServiceNotBounded");
        }

    }

    private class GameService implements ServiceConnection {
        public void onServiceConnected (ComponentName name, IBinder boundService) {
            gameServiceInterface = IGameServiceInterface.Stub
                    .asInterface(boundService);
            try {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        String[] data = Result.split(",");
                        BindNotificationService(notificationListener, data[1], data[0]);
                        InitCallback.OnCallback("Success");
                    }

                    @Override
                    public void OnError (String Error) {
                        InitCallback.OnError(Error);
                    }
                };
                gameServiceInterface.InitService(clientId, clientSecret, DeviceInformationUtil.GetSystemInfo(UnityActivity, false).ToJSON(), iAsyncGameServiceCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected (ComponentName name) {
            gameServiceInterface = null;
        }
    }
}
