package ir.FiroozehCorp.GameService.UnityPackage.Native.Handlers;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;

import ir.FiroozehCorp.GameService.UnityPackage.Interfaces.IGameServiceCallback;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Dialogs.DownloadOBBDialog;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.DownloadListener;
import ir.FiroozehCorp.GameService.UnityPackage.Utils.FileUtil;

public final class DownloadHandler {

    public static DownloadManager downloadManager;
    public static Long DownloadTag;
    private static DownloadHandler Instance;
    public static Activity UnityActivity;

    public DownloadHandler () {
        Instance = this;
    }

    public static DownloadHandler GetInstance () {
        if (Instance == null) Instance = new DownloadHandler();
        return Instance;
    }

    public void SetUnityContext (Activity activity) {
        UnityActivity = activity;
        downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void DownloadObbDataFile (String GameName, String ObbDataTAG, final IGameServiceCallback callback) {
        if (ObbDataTAG != null && !ObbDataTAG.isEmpty()) {
            if (FileUtil.IsNeedToDownloadData(UnityActivity)) {
                DownloadOBBDialog.init(UnityActivity, GameName, ObbDataTAG, new DownloadListener() {
                    @Override
                    public void onDone () {
                        callback.OnCallback("Data_Download_Finished");
                    }

                    @Override
                    public void onError (String error) {
                        callback.OnError(error);
                    }
                });
            } else callback.OnCallback("Data_Downloaded");
        } else callback.OnError("InvalidInput");

    }

    public boolean CancelDownload () {
        return downloadManager.remove(DownloadTag) != 0;
    }


}
