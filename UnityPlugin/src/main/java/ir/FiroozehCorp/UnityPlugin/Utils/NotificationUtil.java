package ir.FiroozehCorp.UnityPlugin.Utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

import ir.FiroozehCorp.UnityPlugin.Native.Models.Achievement;
import ir.FiroozehCorp.UnityPlugin.Native.Models.LeaderBoard;
import ir.FiroozehCorp.UnityPlugin.R;

public final class NotificationUtil {

    public static void NotifyAchievement (Activity activity, Achievement achievement) {

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(activity);
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.achievement_logo));
        builder.setContentTitle(achievement.getName());
        builder.setContentText(achievement.getDescription());

        Notification notification = builder.getNotification();

        notificationManager.notify(1110, notification);


    }

    public static void NotifySubmitScore (Activity activity, int Score, LeaderBoard leaderBoard) {

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(activity);
        builder.setAutoCancel(false);
        builder.setOngoing(false);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.achievement_logo));
        builder.setContentTitle(leaderBoard.getName());
        builder.setContentText("امتیاز" + Score);

        Notification notification = builder.getNotification();

        notificationManager.notify(1111, notification);


    }

}
