package ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces;

public interface LoginListener {
    void onFinish ();

    void onError (String error);

    void onDismiss ();
}
