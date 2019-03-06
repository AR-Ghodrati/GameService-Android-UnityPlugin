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

import ir.firoozeh.unitymodule.Interfaces.InstallListener;

public final class DialogUtil {

    @SuppressLint ("SetTextI18n")
    public static void ShowInstallAppDialog (final Activity UnityActivity, final InstallListener listener) {

        if (UnityActivity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UnityActivity);
            builder.setTitle("نصب گیم سرویس");
            builder.setCancelable(false);

            if (getApplicationName(UnityActivity) != null)
                builder.setMessage("برای استفاده از خدمات آنلاین،نیاز به نصب برنامه گیم سرویس دارید"
                );

            builder.setPositiveButton("کافه بازار", null);
            builder.setNegativeButton("دریافت مستقیم", null);
            builder.setNeutralButton("بیخیال", null);


            final AlertDialog dialog = builder.create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow (DialogInterface _dialog) {
                    Button getServiceـBazaar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button dismiss = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    Button getServiceـDirect = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    getServiceـBazaar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://cafebazaar.ir/app/ir.firoozeh.gameservice/?l=fa"));
                            UnityActivity.startActivity(Intent.createChooser(intent
                                    , "یک برنامه انتخاب کنید:"));
                            UnityActivity.finish();
                        }
                    });

                    getServiceـDirect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://gamesservice.ir/download/app"));
                            UnityActivity.startActivity(Intent.createChooser(intent
                                    , "یک برنامه انتخاب کنید:"));
                            UnityActivity.finish();
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
