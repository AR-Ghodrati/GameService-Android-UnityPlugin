package ir.FiroozehCorp.GameService.UnityPackage.Native.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.net.URI;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Contrast.URLs;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.CloseListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.WSClientUtil;

public class GSNotificationService extends IntentService implements CloseListener {

    public static final String TAG = "GSNotificationService";
    public static boolean isLogEnable;
    public static boolean isWsConnected;
    public static int GSNotificationID = 1000;
    public static boolean AllowRebind = true;

    private NotificationListener listener;
    private String GameID;
    private String JWT;
    private WSClientUtil wsClientUtil;

    public GSNotificationService () {
        super(GSNotificationService.TAG);
    }

    public void ConnectWS () {
        if (!isWsConnected && GameID != null) {
            try {
                isWsConnected = true;
                wsClientUtil = new WSClientUtil(
                        new URI(URLs.WSURI + "?token=" + JWT + "&game=" + GameID)
                        , this, isLogEnable, listener, this);
                wsClientUtil.connect();
            } catch (Exception e) {
                isWsConnected = false;
                if (isLogEnable)
                    Log.e(TAG, e.toString());
            }
        }
    }

    public void StartWSClient (String GameID, String JWT, NotificationListener listener) {
        this.listener = listener;
        this.GameID = GameID;
        this.JWT = JWT;
        ConnectWS();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        ConnectWS();
        return START_STICKY;
    }

    @Override
    public IBinder onBind (Intent intent) {
        Log.e(TAG, "onBind");

        ConnectWS();
        return new LocalBinder();
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        Log.e(TAG, "onHandleIntent");
    }

    @Override
    public void onRebind (Intent intent) {
        Log.e(TAG, "onRebind");

        ConnectWS();
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind (Intent intent) {
        Log.e(TAG, "onUnbind");
        return AllowRebind;
    }

    @Override
    public void onDestroy () {
        Log.e(TAG, "onDestroy");

        try {
            wsClientUtil.close();
            GSNotificationID = 1000;
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, e.toString());
        }
        super.onDestroy();
    }

    @Override
    public void onClose () {
        if (isLogEnable)
            Log.e(TAG, "Open Again...");
        ConnectWS();
    }

    public class LocalBinder extends Binder {
        public GSNotificationService get () {
            return GSNotificationService.this;
        }
    }
}
