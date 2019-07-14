package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Notification;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Services.GSNotificationService;

import static ir.FiroozehCorp.GameService.UnityPackage.Native.Services.GSNotificationService.TAG;


public class WSClientUtil extends WebSocketClient {

    private boolean isLogEnable;
    private Context UnityActivity;
    private Gson gson;
    private NotificationListener listener;


    public WSClientUtil (URI serverUri, Context unityActivity, boolean isLogEnable, NotificationListener listener) {
        super(serverUri);
        this.isLogEnable = isLogEnable;
        this.UnityActivity = unityActivity;
        this.listener = listener;
        this.gson = new Gson();
    }

    public WSClientUtil (URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public WSClientUtil (URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public WSClientUtil (URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public WSClientUtil (URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen (ServerHandshake serverHandshake) {
        if (isLogEnable)
            Log.d(TAG, "Notification Service Is Running...");
    }

    @Override
    public void onMessage (String s) {
        try {
            Notification notification = gson.fromJson(s, Notification.class);
            NotificationUtil.NotifyWSNotification(UnityActivity, notification, listener);
        } catch (Exception e) {
            if (isLogEnable)
                Log.e(TAG, "onMessage EX : " + e.toString());
        }
    }

    @Override
    public void onClose (int i, String s, boolean b) {
        GSNotificationService.isWsConnected = false;
        if (isLogEnable) Log.e(TAG, "Notification Service Closed!");
    }

    @Override
    public void onError (Exception e) {
        if (isLogEnable) Log.e(TAG, e.toString());
    }
}
