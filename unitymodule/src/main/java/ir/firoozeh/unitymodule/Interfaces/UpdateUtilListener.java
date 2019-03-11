package ir.firoozeh.unitymodule.Interfaces;

public interface UpdateUtilListener {
    void onUpdateAvailable (String ChangeLog, boolean MustUpdate);

    void onHaveLastVersion ();

    void onForceInit ();
}
