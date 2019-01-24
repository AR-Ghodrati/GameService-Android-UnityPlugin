package ir.firoozeh.unitymodule.Handlers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import ir.firoozeh.gameservice.ILoginGameServiceInterface;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback;
import ir.firoozeh.unitymodule.Interfaces.IGameServiceLoginCheck;

/**
 * Created by Alireza Ghodrati on 03/10/2018 in
 * Time 11:34 AM for App ir.firoozeh.unitymodule
 */

public final class UnityLogin {

    private static UnityLogin Instance;
    private final String TAG = getClass().getName();
    private ILoginGameServiceInterface loginGameServiceInterface;
    private LoginService loginTestService;
    private Context context;
    private IGameServiceCallback loginCheck;

    public UnityLogin () {
        Instance = this;
    }

    public static UnityLogin GetInstance () {
        if (Instance == null) Instance = new UnityLogin();
        return Instance;

    }

    public void SetUnityContext (Activity activity) {
        this.context = activity.getApplicationContext();
    }

    public void InitLoginService (IGameServiceCallback callback) {
        this.loginCheck = callback;
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
            } else callback.OnError("GameServiceNotInstalled");
        } catch (Exception e) {
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

    public void ShowLoginUI (IGameServiceLoginCheck check) {
        try {
            if (loginGameServiceInterface != null)
                loginGameServiceInterface.ShowLoginUI();
            else check.OnError("UnreachableService");
        } catch (Exception e) {
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
