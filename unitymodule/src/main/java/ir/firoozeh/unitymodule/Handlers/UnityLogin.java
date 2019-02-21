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
import ir.firoozeh.gameservice.ILoginGameServiceInterface;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceLoginCheck;
import ir.firoozeh.unitymodule.Interfaces.InstallListener;
import ir.firoozeh.unitymodule.Utils.DialogUtil;

/**
 * Created by Alireza Ghodrati on 03/10/2018 in
 * Time 11:34 AM for App ir.firoozeh.unitymodule
 */

public final class UnityLogin implements InstallListener {

    private static UnityLogin Instance;
    private final String TAG = getClass().getName();
    private ILoginGameServiceInterface loginGameServiceInterface;
    private LoginService loginTestService;
    private Context context;
    private Activity activity;
    private IGameServiceCallback loginCheck;
    private boolean CheckAppStatus = true;


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

    public void InitLoginService (boolean CheckAppStatus, IGameServiceCallback callback) {
        this.loginCheck = callback;
        this.CheckAppStatus = CheckAppStatus;
        initLoginService(callback);
    }

    public void DisposeService () {
        releaseLoginService();
    }

    private void initLoginService (IGameServiceCallback callback) {

        try {
            if (isPackageInstalled("ir.firoozeh.gameservice", context.getPackageManager())) {
                loginTestService = new LoginService();
                Intent i = new Intent(
                        "ir.firoozeh.gameservice.Services.LoginService.BIND");
                i.setPackage("ir.firoozeh.gameservice");
                boolean ret = context.bindService(i, loginTestService, Context.BIND_AUTO_CREATE);

                if (!ret)
                    callback.OnError("GameServiceNotBounded");

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
                    public void OnCallback (String Result) throws RemoteException {
                        check.isLoggedIn(true);
                    }

                    @Override
                    public void OnError (String Error) throws RemoteException {
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

    private boolean isPackageInstalled (String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onInstallDone () {
        initLoginService(loginCheck);
    }

    @Override
    public void onDismiss () {
        loginCheck.OnError("GameServiceInstallDialogDismiss");
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
