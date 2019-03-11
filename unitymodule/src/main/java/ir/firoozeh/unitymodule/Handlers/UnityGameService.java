package ir.firoozeh.unitymodule.Handlers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import ir.firoozeh.gameservice.IAsyncGameServiceCallback;
import ir.firoozeh.gameservice.IGameServiceInterface;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback;
import ir.firoozeh.unitymodule.Interfaces.InstallDialogListener;
import ir.firoozeh.unitymodule.Utils.DeviceInformationUtil;

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

    private String clientId, clientSecret;
    private IGameServiceCallback InitCallback;

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

    public void InitGameService (String clientId, String clientSecret
            , IGameServiceCallback callback) {

        if (clientId != null && clientSecret != null
                && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.InitCallback = callback;

            initGameService(InitCallback);

        } else callback.OnError("InvalidInputs");
    }

    public void DisposeService () {
        releaseGameService();
    }

    private void releaseGameService () {
        this.context.unbindService(gameService);
        gameService = null;
        Log.d(TAG, "releaseGameService(): unbound.");
    }

    private void initGameService (IGameServiceCallback callback) {

        try {
            if (isPackageInstalled(context.getPackageManager())) {

                Log.e(TAG, "initGameService()");

                gameService = new GameService();

                Intent i = new Intent(
                        "ir.firoozeh.gameservice.Services.GameService.BIND");
                i.setPackage("ir.firoozeh.gameservice");

                boolean ret = context.bindService(i, gameService, Context.BIND_AUTO_CREATE);
                if (!ret)
                    callback.OnError("GameServiceNotBounded");
            }
        } catch (Exception e) {
            Log.e(TAG, "initGameService() Exception:" + e.toString());
            callback.OnError("GameServiceException");
        }
    }

    public void GetAchievement (final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestAchievement(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void GetSDKVersion (final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestVersion(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void GetLeaderBoards (final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestLeaderBoards(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void GetLeaderBoardData (String LeaderBoardID, final IGameServiceCallback callback) {
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
                gameServiceInterface.RequestLeaderBoardData(LeaderBoardID, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowLeaderBoardUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowLeaderBoardUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowAchievementUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowAchievementUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowSurveyUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowSurveyUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void ShowGamePageUI (final IGameServiceCallback callback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.ShowGamePageUI(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void UnlockAchievement (String AchievementID, boolean HaveNotification, final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestUnlockAchievement(AchievementID, HaveNotification, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void SubmitScore (String Id, int Score, boolean HaveNotification, final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestSubmitScore(Id, Score, HaveNotification, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void GetLastSave (final IGameServiceCallback callback) {

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
                gameServiceInterface.RequestGetData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void SaveData (String Name, String Description, String Cover, String SaveData, final IGameServiceCallback callback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService");
            } else {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {

                    }

                    @Override
                    public void OnError (String Error) {
                        callback.OnError(Error);
                    }
                };
                gameServiceInterface.RequestSaveData(Name, Description, Cover, SaveData, iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void RemoveLastSave (final IGameServiceCallback callback) {
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
                gameServiceInterface.RequestRemoveSaveData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }

    public void GetUserData (final IGameServiceCallback callback) {
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
                gameServiceInterface.RequestGetUserData(iAsyncGameServiceCallback);
            }
        } catch (Exception e) {
            callback.OnError("Exception:" + e.toString());
        }
    }



    private boolean isPackageInstalled (PackageManager packageManager) {
        try {
            packageManager.getPackageInfo("ir.firoozeh.gameservice", 0);
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
    public void onInstallDone () {
        initGameService(InitCallback);
    }

    @Override
    public void onDismiss () {
        InitCallback.OnError("GameServiceInstallDialogDismiss");
    }

    private class GameService implements ServiceConnection {
        public void onServiceConnected (ComponentName name, IBinder boundService) {
            gameServiceInterface = IGameServiceInterface.Stub
                    .asInterface(boundService);
            try {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        InitCallback.OnCallback("Success");
                    }

                    @Override
                    public void OnError (String Error) {
                        InitCallback.OnError(Error);
                    }
                };
                gameServiceInterface.InitService(clientId, clientSecret, DeviceInformationUtil.GetSystemInfo(UnityActivity).ToJSON(), iAsyncGameServiceCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "GameService:Connected");
        }

        public void onServiceDisconnected (ComponentName name) {
            gameServiceInterface = null;
            Log.e(TAG, "GameServiceTest(): Disconnected");
        }
    }
}
