package ir.FiroozehCorp.GameService.UnityPackage.Native.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Contrast.URLs;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.NativeUtil;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.WSClientUtil;

public class GSNotificationService extends Service {


    public static final String TAG = "GSNotificationService";
    public static boolean isLogEnable;
    public static int GSNotificationID = 1000;
    private WSClientUtil wsClientUtil;

    public void StartWSClient (String GameID, NotificationListener listener) {
        try {
            wsClientUtil = new WSClientUtil(
                    new URI(URLs.WSURI + NativeUtil.GetJWT(this) + "&game=" + GameID)
                    , this, isLogEnable, listener);
            wsClientUtil.connect();
        } catch (URISyntaxException e) {
            if (isLogEnable)
                Log.e(TAG, e.toString());
        }
    }

    @Override
    public IBinder onBind (Intent intent) {
        return new LocalBinder();
    }

    @Override
    public boolean onUnbind (Intent intent) {
        try {
            // wsClientUtil.close();
            // GSNotificationID = 1000;
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, e.toString());
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy () {
        try {
            // wsClientUtil.close();
            //  GSNotificationID = 1000;
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, e.toString());
        }
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public GSNotificationService get () {
            return GSNotificationService.this;
        }
    }
}
