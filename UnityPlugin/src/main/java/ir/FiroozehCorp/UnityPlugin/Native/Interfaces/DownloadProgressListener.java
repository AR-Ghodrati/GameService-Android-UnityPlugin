package ir.FiroozehCorp.UnityPlugin.Native.Interfaces;

public interface DownloadProgressListener {
    void onProgress (int progress, int total);

    void onDone ();
}
