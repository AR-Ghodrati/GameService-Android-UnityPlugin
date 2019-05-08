package ir.FiroozehCorp.UnityPlugin.App.Interfaces;

public interface UpdateUtilListener {
    void onUpdateAvailable (String ChangeLog, boolean MustUpdate);

    void onHaveLastVersion ();

    void onForceInit ();
}
