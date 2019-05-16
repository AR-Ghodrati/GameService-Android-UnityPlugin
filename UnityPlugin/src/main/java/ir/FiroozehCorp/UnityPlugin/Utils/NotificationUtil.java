package ir.FiroozehCorp.UnityPlugin.Utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Pair;

import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.ImageLoadListener;
import ir.FiroozehCorp.UnityPlugin.Native.Models.Achievement;
import ir.FiroozehCorp.UnityPlugin.Native.Models.LeaderBoard;
import ir.FiroozehCorp.UnityPlugin.R;

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


}
