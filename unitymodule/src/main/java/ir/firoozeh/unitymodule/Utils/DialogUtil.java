package ir.firoozeh.unitymodule.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ir.firoozeh.unitymodule.Interfaces.InstallListener;

public final class DialogUtil {

    @SuppressLint ("SetTextI18n")
    public static void ShowInstallAppDialog (final Activity UnityActivity, final InstallListener listener) {

        if (UnityActivity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UnityActivity);
            builder.setTitle("نصب گیم سرویس");
            builder.setCancelable(false);

            if (getApplicationName(UnityActivity) != null)
                builder.setMessage("بازی \" " + getApplicationName(UnityActivity) + "\" از گیم سرویس استفاده می کند،برای دریافت آن کلیک کنید ");
            else
                builder.setMessage("این بازی از گیم سرویس استفاده می کند،برای دریافت آن کلیک کنید");

            builder.setPositiveButton("دریافت", null);
            builder.setNegativeButton("فعلا استفاده نمی کنم", null);
            builder.setNeutralButton("بررسی نصب", null);


            final AlertDialog dialog = builder.create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow (DialogInterface _dialog) {
                    Button getService = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button checkService = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    Button dismiss = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    getService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://gameservice.liara.run"));
                            UnityActivity.startActivity(Intent.createChooser(intent
                                    , "یک برنامه برای دریافت انتخاب کنید:"));
                        }
                    });

                    checkService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View v) {
                            if (isPackageInstalled(UnityActivity.getPackageManager()))
                                listener.onInstallDone();
                            else
                                Toast.makeText(UnityActivity, "گیم سرویس نصب نمی باشد", Toast.LENGTH_LONG).show();

                        }
                    });

                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View v) {
                            listener.onDismiss();
                            dialog.dismiss();
                        }
                    });

                }
            });
            dialog.show();

        }
    }

    private static String getApplicationName (Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }


    private static boolean isPackageInstalled (PackageManager packageManager) {
        try {
            packageManager.getPackageInfo("ir.firoozeh.gameservice", 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
