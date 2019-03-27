package ir.FiroozehCorp.UnityPlugin.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import ir.FiroozehCorp.UnityPlugin.Interfaces.InstallDialogListener;
import ir.FiroozehCorp.UnityPlugin.Interfaces.UpdateDialogListener;

public final class DialogUtil {

    @SuppressLint ("SetTextI18n")
    public static void ShowInstallAppDialog (final Activity UnityActivity, final InstallDialogListener listener) {

        if (UnityActivity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UnityActivity);
            builder.setTitle("نصب گیم سرویس");
            builder.setCancelable(false);

            builder.setMessage("برای استفاده از خدمات آنلاین،نیاز به نصب برنامه گیم سرویس دارید");

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

    @SuppressLint ("SetTextI18n")
    public static void ShowUpdateAppDialog (
            final Activity UnityActivity
            , final boolean MustUpdate
            , final String ChangeLog
            , final UpdateDialogListener listener) {

        if (UnityActivity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UnityActivity);
            builder.setTitle("بروزرسانی گیم سرویس");
            builder.setCancelable(false);


            builder.setMessage(" برای استفاده از خدمات آنلاین،نیاز به بروزرسانی برنامه گیم سرویس دارید."
                    + "\n"
                    + "تغییرات نسخه جدید:"
                    + "\n"
                    + ChangeLog);

            builder.setPositiveButton("کافه بازار", null);
            builder.setNegativeButton("دریافت مستقیم", null);

            if (!MustUpdate) {
                builder.setNeutralButton("بیخیال", null);
                builder.setCancelable(true);
            }


            final AlertDialog dialog = builder.create();


            if (!MustUpdate)
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss (DialogInterface dialog) {
                        listener.onUpdateDismiss();
                    }
                });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow (DialogInterface _dialog) {
                    Button getServiceـBazaar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button getServiceـDirect = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    getServiceـBazaar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick (View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://cafebazaar.ir/app/ir.FiroozehCorp.GameService/?l=fa"));
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

                    if (!MustUpdate) {
                        Button dismiss = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                        dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick (View v) {
                                dialog.dismiss();
                            }
                        });
                    }


                }
            });
            dialog.show();

        }
    }

}
