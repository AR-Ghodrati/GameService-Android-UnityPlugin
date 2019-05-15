package ir.FiroozehCorp.UnityPlugin.Native.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.DownloadListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.DownloadProgressListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.UnityPlugin.R;
import ir.FiroozehCorp.UnityPlugin.Utils.ApiRequestUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.ConnectivityUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.FileUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.NativeUtil;

import static ir.FiroozehCorp.UnityPlugin.Native.Handlers.UnityGameServiceNative.UnityActivity;

public class DowloadOBBDialog extends Dialog {

    private static final String TAG = "DowloadOBBDialog";
    private LinearLayout LoadingBanner, ErrorBanner, baseBanner;
    private TextView ErrorDes, DownloadProgressTxtSize, DownloadProgressTxt;
    private ProgressBar DownloadProgress;
    private String DataName;
    private DownloadListener listener;

    public DowloadOBBDialog (Context context) {
        super(context);
    }

    public void setData (String tag, DownloadListener listener) {
        this.DataName = tag;
        this.listener = listener;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setContentView(R.layout.activity_download_gamedata);
        setCancelable(false);


        int width;
        int height;

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.7);
            height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.5);
        } else {
            width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
            height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.7);
        }

        if (getWindow() != null)
            getWindow().setLayout(width, height);


        LoadingBanner = findViewById(R.id.LoadingBanner);
        ErrorBanner = findViewById(R.id.ErrorBanner);
        baseBanner = findViewById(R.id.BaseRoot);

        DownloadProgress = findViewById(R.id.DownloadProgress);

        ErrorDes = findViewById(R.id.ErrorDes);
        DownloadProgressTxt = findViewById(R.id.DownloadProgressTxt);
        DownloadProgressTxtSize = findViewById(R.id.DownloadProgressTxtSize);


        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {

            ApiRequestUtil.getDataPackInfo(UnityActivity, DataName
                    , new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {
                                LoadingBanner.setVisibility(View.GONE);


                                final long size = object.getJSONObject("data").getLong("size");
                                String link = object.getJSONObject("data").getString("downloadLink");

                                if (FileUtil.IsFreeSpaceToDownload(size)) {
                                    baseBanner.setVisibility(View.VISIBLE);
                                    DownloadProgress.setIndeterminate(false);


                                    FileUtil.DownloadDataFile(UnityActivity, link, DataName
                                            , new DownloadProgressListener() {
                                                @Override
                                                public void onProgress (final int progress, final int total) {

                                                    DownloadProgressTxtSize.setText(FileUtil.readableFileSize(progress)
                                                            + " از " + FileUtil.readableFileSize(total));


                                                    DownloadProgress.setMax(total);
                                                    DownloadProgress.setProgress(progress);


                                                    final int current = (progress * 100) / total;
                                                    DownloadProgressTxt.setText(current + "%");

                                                }

                                                @Override
                                                public void onDone () {
                                                    NativeUtil.setOBBMetaData(UnityActivity, DataName, size);
                                                    dismiss();
                                                    listener.onDone();
                                                }
                                            });

                                } else {
                                    ErrorBanner.setVisibility(View.VISIBLE);
                                    ErrorDes.setText("برای دریافت دیتای بازی به "
                                            + FileUtil.readableFileSize(size)
                                            + " فضای خالی نیاز است.");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError (String error) {

                        }
                    });

        } else {
            ErrorBanner.setVisibility(View.VISIBLE);
            LoadingBanner.setVisibility(View.GONE);
            ErrorDes.setText("برای دریافت دیتای بازی به اینترنت متصل شوید و دوباره امتحان کنید");
        }


        super.onCreate(savedInstanceState);
    }


}
