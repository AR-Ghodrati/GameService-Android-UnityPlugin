package ir.FiroozehCorp.UnityPlugin.Native.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Pair;

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

public final class DownloadOBBDialog {

    private static final int ON_RESPONSE = 1;
    private static final int ON_PROGRESS = 2;


    public static void init (final Activity activity, final String GameName, final String tag, final DownloadListener listener) {

        final ProgressDialog downloadDialog = new ProgressDialog(activity);
        downloadDialog.setTitle("گیم سرویس");
        downloadDialog.setMessage("درحال دانلود دیتای بازی...");
        downloadDialog.setCancelable(false);
        downloadDialog.setIndeterminate(true);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setIcon(R.drawable.logo);
        downloadDialog.setProgressStyle(STYLE_HORIZONTAL);
        downloadDialog.setProgressNumberFormat(null);


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
                init(activity, GameName, tag, listener);
            }
        });
        noInternetDialog.setIcon(R.drawable.logo);
        noInternetDialog.setCancelable(false);
        final AlertDialog noInternet = noInternetDialog.create();


        final Handler handler = new Handler(
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage (Message msg) {
                        if (msg.what == ON_RESPONSE) {
                            downloadDialog.setIndeterminate(false);
                            downloadDialog.setMax((int) msg.obj);
                        } else if (msg.what == ON_PROGRESS) {
                            Pair<Integer, String> data = (Pair<Integer, String>) msg.obj;
                            downloadDialog.setProgress(data.first);
                            downloadDialog.setMessage(data.second);
                        }
                        return true;
                    }
                });


        if (ConnectivityUtil.isNetworkConnected(activity)) {
            downloadDialog.show();

            ApiRequestUtil.getDataPackInfo(activity, GameName, tag
                    , new JsonObjectCallbackListener() {
                        @Override
                        public void onResponse (JSONObject object) {
                            try {
                                final long size = object.getJSONObject("data").getLong("size");
                                String link = object.getJSONObject("data").getString("downloadLink");

                                if (FileUtil.IsFreeSpaceToDownload(size)) {

                                    handler.obtainMessage(ON_RESPONSE, (int) size)
                                            .sendToTarget();


                                    FileUtil.DownloadDataFile(activity, link, tag
                                            , new DownloadProgressListener() {
                                                @Override
                                                public void onProgress (final int progress) {
                                                    String txt = "درحال دانلود دیتای بازی..." + "\n(" + FileUtil.readableFileSize(progress) + " از " + FileUtil.readableFileSize(size) + ")";
                                                    handler.obtainMessage(ON_PROGRESS, Pair.create(progress, txt))
                                                            .sendToTarget();
                                                }

                                                @Override
                                                public void onDone () {
                                                    NativeUtil.setOBBMetaData(activity, tag, size);
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
                                    noSpaceDialog.setPositiveButton("بستن بازی", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick (DialogInterface dialog, int which) {
                                            listener.onError("Data_Download_Dismissed");
                                            dialog.dismiss();
                                        }
                                    });

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
