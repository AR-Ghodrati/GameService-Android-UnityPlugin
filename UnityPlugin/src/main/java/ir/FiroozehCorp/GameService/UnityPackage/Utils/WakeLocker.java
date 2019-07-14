package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Alireza Ghodrati on 15/04/2018.
 */

public abstract class WakeLocker {

    private static PowerManager.WakeLock wakeLock;

    public static void acquire (Context context) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, context.getPackageName() + "WakeLockUtil");
        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
    }

    public static void release () {
        if (wakeLock != null) wakeLock.release();
        wakeLock = null;
    }
}
