package ir.FiroozehCorp.UnityPlugin.Native.Interfaces;

public interface LoginListener {
    void onFinish ();

    void onError (String error);

    void onDismiss ();
}
