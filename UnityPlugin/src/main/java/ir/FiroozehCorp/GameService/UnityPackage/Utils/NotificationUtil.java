package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;
import android.widget.Toast;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.ImageLoadListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.NotificationListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Achievement;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Models.LeaderBoard;
import ir.FiroozehCorp.GameService.UnityPackage.R;

import static ir.FiroozehCorp.GameService.UnityPackage.Native.Services.GSNotificationService.GSNotificationID;

public final class NotificationUtil {

    public static void NotifyAchievement (Activity activity, Achievement achievement) {

        String id = "GameServiceUnity";
        final NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        int icon = R.drawable.logo;

        try {
            icon = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0).icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Notification.Builder builder = new Notification.Builder(activity);
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.setSmallIcon(icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            builder.setPriority(Notification.PRIORITY_MAX);

        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.achievement_logo));
        builder.setContentTitle(achievement.getName());
        builder.setContentText(achievement.getDescription());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, "GameServiceUnityNotification",
                    importance);
            channel.setDescription("GameServiceUnityNotificationChannel");
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(id);
        }

        ImageLoadListener listener = new ImageLoadListener() {
            @Override
            public void onLoaded (Bitmap bitmap) {
                if (bitmap != null) builder.setLargeIcon(bitmap);
                Notification notification = builder.getNotification();
                notificationManager.notify("GameServiceUnity", 0, notification);
            }
        };

        new FileUtil.LoadImageFromURL().execute(Pair.create(achievement.getCover(), listener));

    }

    public static void NotifySubmitScore (Activity activity, int Score, LeaderBoard leaderBoard) {

        String id = "GameServiceUnity";
        final NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.logo;

        try {
            icon = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0).icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Notification.Builder builder = new Notification.Builder(activity);
        builder.setAutoCancel(false);
        builder.setOngoing(false);
        builder.setSmallIcon(icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            builder.setPriority(Notification.PRIORITY_MAX);

        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.achievement_logo));
        builder.setContentTitle(leaderBoard.getName());
        builder.setContentText(Score + " امتیاز ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, "GameServiceUnityNotification",
                    importance);
            channel.setDescription("GameServiceUnityNotificationChannel");
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(id);
        }

        ImageLoadListener listener = new ImageLoadListener() {
            @Override
            public void onLoaded (Bitmap bitmap) {
                if (bitmap != null) builder.setLargeIcon(bitmap);
                Notification notification = builder.getNotification();
                notificationManager.notify("GameServiceUnity", 0, notification);
            }
        };

        new FileUtil.LoadImageFromURL().execute(Pair.create(leaderBoard.getCover(), listener));

    }

    public static void NotifyWSNotification (Context activity, ir.FiroozehCorp.GameService.UnityPackage.Native.Models.Notification notification, NotificationListener listener) {
        if (notification.HaveJsonData() && listener != null)
            listener.onData(notification.getJsonData());
        if (!notification.IsOnlyJson()) {
            String id = "GameServiceUnity";
            final NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

            int icon = R.drawable.logo;

            try {
                icon = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0).icon;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            final Notification.Builder builder = new Notification.Builder(activity);
            builder.setAutoCancel(true);
            builder.setOnlyAlertOnce(true);
            builder.setSmallIcon(icon);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                builder.setPriority(Notification.PRIORITY_MAX);

            builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), icon));
            builder.setContentTitle(notification.getTitle());
            builder.setContentText(notification.getDescription());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(id, "GameServiceUnityNotification",
                        importance);
                channel.setDescription("GameServiceUnityNotificationChannel");
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(id);
            }

            switch (notification.getTapActionType()) {

                case OPEN_APP:
                    Intent notifyIntent = activity.getPackageManager()
                            .getLaunchIntentForPackage(activity.getPackageName());
                    if (notifyIntent != null) {
                        notifyIntent.setAction(Intent.ACTION_MAIN);
                        notifyIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        PendingIntent notifyPendingIntent = PendingIntent
                                .getActivity(activity, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(notifyPendingIntent);
                    }
                    break;
                case OPEN_LINK:
                    Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notification.getTapAction().getLinkAddress()));
                    PendingIntent notifyPendingIntent = PendingIntent
                            .getActivity(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(notifyPendingIntent);
                    break;
                case OPEN_MARKET:
                    switch (notification.getTapAction().getMarketType()) {
                        case BAZAAR:
                            Intent notificationIntentBAZAAR;
                            try {
                                notificationIntentBAZAAR = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("bazaar://details?id=" + notification.getTapAction().getPackageName()));
                                notificationIntentBAZAAR.setPackage("com.farsitel.bazaar");
                            } catch (ActivityNotFoundException e) {
                                notificationIntentBAZAAR = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://cafebazaar.ir/app/" + notification.getTapAction().getPackageName() + "/?l=fa")
                                );
                            }
                            PendingIntent notifyPendingIntentBazaar = PendingIntent
                                    .getActivity(activity, 0, notificationIntentBAZAAR, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(notifyPendingIntentBazaar);
                            break;
                        case Myket:
                            Intent notificationIntentMyket;
                            try {
                                notificationIntentMyket = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("myket://details?id=" + notification.getTapAction().getPackageName()));
                            } catch (ActivityNotFoundException e) {
                                notificationIntentMyket = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://myket.ir/app/" + notification.getTapAction().getPackageName() + "/?l=fa")
                                );
                            }
                            PendingIntent notifyPendingIntentMyket = PendingIntent
                                    .getActivity(activity, 0, notificationIntentMyket, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(notifyPendingIntentMyket);
                            break;
                        case IranApps:
                            Intent notificationIntentIranApps;
                            try {
                                notificationIntentIranApps = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("iranapps://app/" + notification.getTapAction().getPackageName()));
                            } catch (ActivityNotFoundException e) {
                                notificationIntentIranApps = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://iranapps.ir/app/" + notification.getTapAction().getPackageName() + "/?l=fa")
                                );
                            }
                            PendingIntent notifyPendingIntentIranApps = PendingIntent
                                    .getActivity(activity, 0, notificationIntentIranApps, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(notifyPendingIntentIranApps);
                            break;
                        case GooglePlay:
                            Intent notificationIntentGooglePlay;
                            try {
                                notificationIntentGooglePlay = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=" + notification.getTapAction().getPackageName()));
                            } catch (ActivityNotFoundException e) {
                                notificationIntentGooglePlay = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=" + notification.getTapAction().getPackageName() + "/?l=fa")
                                );
                            }
                            PendingIntent notifyPendingIntentGooglePlay = PendingIntent
                                    .getActivity(activity, 0, notificationIntentGooglePlay, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(notifyPendingIntentGooglePlay);
                            break;
                    }
                    break;
                case CLOSE_NOTIFICATION:
                    builder.setAutoCancel(true);
                    break;
                case OPEN_EMAIL:
                    try {
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{notification.getTapAction().getEmailAddress()});
                        email.putExtra(Intent.EXTRA_SUBJECT, notification.getTapAction().getEmailTitle());
                        email.putExtra(Intent.EXTRA_TEXT, notification.getTapAction().getEmailBody());
                        email.setType("message/rfc822");

                        PendingIntent contentIntent = PendingIntent.getActivity(
                                activity, 0,
                                Intent.createChooser(email, "یک برنامه برای ارسال ایمیل انتخاب کنید:"), PendingIntent.FLAG_UPDATE_CURRENT
                        );
                        builder.setContentIntent(contentIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity, "برنامه ای برای ارسال ایمیل پیدا نشد", Toast.LENGTH_LONG).show();
                    }
                    break;
                case INVITE_TELEGRAM_CHANNEL:
                    Intent notificationIntentTg;
                    try {
                        notificationIntentTg = new Intent(Intent.ACTION_VIEW
                                , Uri.parse("tg://resolve?domain=" + notification.getTapAction().getTelegramChennel()));
                    } catch (ActivityNotFoundException e) {
                        notificationIntentTg = new Intent(Intent.ACTION_VIEW
                                , Uri.parse("https://t.me/" + notification.getTapAction().getTelegramChennel()));
                    }
                    PendingIntent contentIntent = PendingIntent.getActivity(
                            activity, 0, notificationIntentTg, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
                    break;
                case OTHER_INTENT:
                    break;
            }

                Notification noti = builder.getNotification();
                notificationManager.notify("GameServiceUnity", GSNotificationID, noti);
                GSNotificationID++;

        }
    }
}
