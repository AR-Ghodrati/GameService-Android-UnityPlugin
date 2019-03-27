package ir.FiroozehCorp.UnityPlugin.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class InstallGameServiceListener extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent) {
        Log.e("intent", intent.toString());
        Toast.makeText(context, "App Installed!!!! " + intent.toString(), Toast.LENGTH_LONG).show();
    }
}
