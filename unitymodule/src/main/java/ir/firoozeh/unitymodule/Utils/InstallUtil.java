package ir.firoozeh.unitymodule.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;

import ir.firoozeh.unitymodule.Receiver.InstallGameServiceListener;

public final class InstallUtil {

    public static void RegisterListener (Activity activity) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addDataScheme("ir.firoozeh.unitymodule");
        activity.registerReceiver(new InstallGameServiceListener(), intentFilter);
    }
}
