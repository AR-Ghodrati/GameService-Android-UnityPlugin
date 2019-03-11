package ir.firoozeh.unitymodule.Handlers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import ir.firoozeh.gameservice.IAsyncGameServiceCallback;
import ir.firoozeh.gameservice.ILoginGameServiceInterface;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceLoginCheck;
import ir.firoozeh.unitymodule.Interfaces.InstallDialogListener;
import ir.firoozeh.unitymodule.Interfaces.UpdateDialogListener;
import ir.firoozeh.unitymodule.Interfaces.UpdateUtilListener;
import ir.firoozeh.unitymodule.Utils.ConnectivityUtil;
import ir.firoozeh.unitymodule.Utils.DialogUtil;
import ir.firoozeh.unitymodule.Utils.UpdateUtil;

/**
 * Created by Alireza Ghodrati on 03/10/2018 in
 * Time 11:34 AM for App ir.firoozeh.unitymodule
 */

public final class UnityLogin implements InstallDialogListener, UpdateDialogListener {

    private static UnityLogin Instance;
    private final String TAG = getClass().getName();
    private ILoginGameServiceInterface loginGameServiceInterface;
    private LoginService loginTestService;
    private Context context;
    private Activity activity;
    private IGameServiceCallback loginCheck;
    private boolean CheckAppStatus = true;
    private boolean CheckOptionalUpdate = true;


    public UnityLogin () {
        Instance = this;
    }

    public static UnityLogin GetInstance () {
        if (Instance == null) Instance = new UnityLogin();
        return Instance;

    }

    public void SetUnityContext (Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void InitLoginService (
            boolean CheckAppStatus
            , boolean CheckOptionalUpdate
            , IGameServiceCallback callback) {

        this.loginCheck = callback;
        this.CheckAppStatus = CheckAppStatus;
        this.CheckOptionalUpdate = CheckOptionalUpdate;
        initLoginService(callback);

    }

    public void DisposeService () {
        releaseLoginService();
    }

    private void initLoginService (final IGameServiceCallback callback) {

        try {
            if (isPackageInstalled(context.getPackageManager())) {
                if (ConnectivityUtil.isNetworkConnected(activity)) {
                    int VerCode = getGameServiceVersionCode(context.getPackageManager());
                    if (VerCode != -1) {
                        UpdateUtil.CheckUpdate(CheckOptionalUpdate, VerCode, new UpdateUtilListener() {
                            @Override
                            public void onUpdateAvailable (String ChangeLog, boolean MustUpdate) {
                                DialogUtil.ShowUpdateAppDialog(activity, MustUpdate, ChangeLog, UnityLogin.this);
                            }

                            @Override
                            public void onHaveLastVersion () {
                                _initLoginService(callback);
                            }

                            @Override
                            public void onForceInit () {
                                _initLoginService(callback);
                            }
                        });
                    }
                } else callback.OnError("NetworkUnreachable");
            } else {
                if (CheckAppStatus)
                    DialogUtil.ShowInstallAppDialog(activity, this);
                else
                    callback.OnError("GameServiceNotInstalled");
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), e.toString());
            callback.OnError("GameServiceException");
        }

    }


    private void _initLoginService (final IGameServiceCallback callback) {
        loginTestService = new LoginService();
        Intent i = new Intent(
                "ir.firoozeh.gameservice.Services.LoginService.BIND");
        i.setPackage("ir.firoozeh.gameservice");
        boolean ret = context.bindService(i, loginTestService, Context.BIND_AUTO_CREATE);

        if (!ret)
            callback.OnError("GameServiceNotBounded");
    }

    private void releaseLoginService () {
        context.unbindService(loginTestService);
        loginTestService = null;
        Log.e(TAG, "releaseLoginService(): unbound.");
    }

    public void IsUserLoggedIn (IGameServiceLoginCheck check) {
        try {
            if (loginGameServiceInterface != null)
                check.isLoggedIn(loginGameServiceInterface.isLoggedIn());
            else check.OnError("UnreachableService");
        } catch (Exception e) {
            check.OnError("GameServiceException");
        }
    }

    public void ShowLoginUI (final IGameServiceLoginCheck check) {
        try {
            if (loginGameServiceInterface != null) {
                IAsyncGameServiceCallback.Stub iAsyncGameServiceCallback = new IAsyncGameServiceCallback.Stub() {
                    @Override
                    public void OnCallback (String Result) {
                        check.isLoggedIn(true);
                    }

                    @Override
                    public void OnError (String Error) {
                        check.OnError(Error);
                    }
                };
                loginGameServiceInterface.ShowLoginUI(iAsyncGameServiceCallback);
            } else check.OnError("UnreachableService");
        } catch (Exception e) {
            Log.e(getClass().getName(), e.toString());
            check.OnError("GameServiceException");
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

    private int getGameServiceVersionCode (PackageManager packageManager) {
        try {
            return packageManager.getPackageInfo("ir.firoozeh.gameservice", 0).versionCode;
        } catch (Exception e) {
            return -1;
        }
    }


    @Override
    public void onDismiss () {
        loginCheck.OnError("GameServiceInstallDialogDismiss");
    }

    @Override
    public void onUpdateDismiss () {
        loginCheck.OnError("GameServiceUpdateDialogDismiss");
    }


    private class LoginService
            implements ServiceConnection {
        public void onServiceConnected (ComponentName name, IBinder boundService) {
            loginGameServiceInterface = ILoginGameServiceInterface.Stub
                    .asInterface(boundService);
            loginCheck.OnCallback("Success");
            Log.e(TAG, "LoginTestService(): Connected");
        }

        public void onServiceDisconnected (ComponentName name) {
            loginGameServiceInterface = null;
            Log.e(TAG, "LoginTestService(): Disconnected");
        }
    }

}
