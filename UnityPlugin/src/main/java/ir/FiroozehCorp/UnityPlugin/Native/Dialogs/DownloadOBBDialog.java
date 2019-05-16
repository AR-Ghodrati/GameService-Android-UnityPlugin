package ir.FiroozehCorp.UnityPlugin.Native.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

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

import static android.app.ProgressDialog.STYLE_HORIZONTAL;
import static ir.FiroozehCorp.UnityPlugin.Native.Handlers.UnityGameServiceNative.UnityActivity;

public final class DownloadOBBDialog {

    public static void init (final Activity activity, final String tag, final DownloadListener listener) {

        final ProgressDialog downloadDialog = new ProgressDialog(activity);
        downloadDialog.setTitle("گیم سرویس");
        downloadDialog.setMessage("درحال دانلود دیتای بازی...");
        downloadDialog.setCancelable(false);
        downloadDialog.setIndeterminate(true);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setIcon(R.drawable.logo);
        downloadDialog.setProgressStyle(STYLE_HORIZONTAL);
        downloadDialog.setMax(100);


        final AlertDialog.Builder noInternetDialog = new AlertDialog.Builder(activity);
        noInternetDialog.setTitle("گیم سرویس");
        noInternetDialog.setMessage("برای دریافت دیتای بازی به اینترنت متصل شوید و دوباره امتحان کنید");
        noInternetDialog.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        noInternetDialog.setNegativeButton("دانلود دیتا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                init(activity, tag, listener);
            }
        });
        noInternetDialog.setIcon(R.drawable.logo);
        noInternetDialog.setCancelable(false);
        final AlertDialog noInternet = noInternetDialog.create();


        if (ConnectivityUtil.isNetworkConnected(UnityActivity)) {
            downloadDialog.show();

            ApiRequestUtil.getDataPackInfo(UnityActivity, tag
                    , new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {
                                final long size = object.getJSONObject("data").getLong("size");
                                String link = object.getJSONObject("data").getString("downloadLink");

                                if (FileUtil.IsFreeSpaceToDownload(size)) {

                                    downloadDialog.setIndeterminate(false);

                                    FileUtil.DownloadDataFile(UnityActivity, link, tag
                                            , new DownloadProgressListener() {
                                                @Override
                                                public void onProgress (final int progress) {

                                                    final int current = (int) ((progress * 100) / size);
                                                    UnityActivity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run () {
                                                            downloadDialog.setProgress(current);
                                                            downloadDialog.setMessage("درحال دانلود دیتای بازی..."
                                                                    + "\n(" + FileUtil.readableFileSize(progress) + " از " + FileUtil.readableFileSize(size) + ")");
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onDone () {
                                                    NativeUtil.setOBBMetaData(UnityActivity, tag, size);
                                                    downloadDialog.dismiss();
                                                    noInternet.dismiss();
                                                    listener.onDone();
                                                }
                                            });

                                } else {
                                    final AlertDialog.Builder noSpaceDialog = new AlertDialog.Builder(activity);
                                    noSpaceDialog.setTitle("گیم سرویس");
                                    noSpaceDialog.setMessage("برای دریافت دیتای بازی به "
                                            + FileUtil.readableFileSize(size)
                                            + " فضای خالی نیاز است.");
                                    noSpaceDialog.setIcon(R.drawable.logo);
                                    noSpaceDialog.setCancelable(false);

                                    downloadDialog.dismiss();
                                    noSpaceDialog.show();
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
            downloadDialog.dismiss();
            noInternet.show();
        }
    }

}
