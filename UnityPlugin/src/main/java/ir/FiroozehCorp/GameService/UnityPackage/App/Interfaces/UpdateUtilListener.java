package ir.FiroozehCorp.GameService.UnityPackage.App.Interfaces;

public interface UpdateUtilListener {
    void onUpdateAvailable (String ChangeLog, boolean MustUpdate);

    void onHaveLastVersion ();

    void onForceInit ();
}
