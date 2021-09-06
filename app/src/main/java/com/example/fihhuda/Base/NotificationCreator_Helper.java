package com.example.fihhuda.Base;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fihhuda.ListenSrvicesManager;
import com.example.fihhuda.R;

public class NotificationCreator_Helper {
   public String PackageName ;
 public   static NotificationCompat.Builder builder;
  static   RemoteViews remoteViews;
  public static NotificationBroadCastReceiver actionBroadcastReceiver;
  public static NotificationManagerCompat notificationManager;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void createNotifications(Context context, String soraname , String packageName){

         actionBroadcastReceiver = new NotificationBroadCastReceiver();
        /// actionBroadcastReceiver.context=getApplicationContext();
        // Get the layouts to use in the custom notification
        remoteViews = new RemoteViews(packageName, R.layout.custom_notification);
        // RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
          remoteViews.setTextViewText(R.id.surah_name,soraname);

        builder = new NotificationCompat.Builder( context,ApplicationClass.CHANNEL_ID)
                .setSmallIcon(R.drawable.quran)
                //.setContentTitle(surahnameIntented)
                // .setContentText("Much longer text that cannot fit one line...")
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomBigContentView(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(false)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);



        // cancel actions
        Intent intent = new Intent("cancel");
        PendingIntent pending = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.not_cancel,pending);
        //previous action
        Intent intentpre = new Intent("prev");
        PendingIntent pendingpre = PendingIntent.getBroadcast(context, 1,
                intentpre, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.not_previous,pendingpre);

        // next action
        Intent intentnext = new Intent("next");
        PendingIntent pendingnext = PendingIntent.getBroadcast(context, 3,
                intentnext, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.not_next,pendingnext);

        // play_puas action
        Intent intentplay_puase = new Intent("play_puase");
        PendingIntent pendingplay_puas = PendingIntent.getBroadcast(context, 4,
                intentplay_puase, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.not_play,pendingplay_puas);


        notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());







    }

    public static void updateNotification(String name){

        int api = Build.VERSION.SDK_INT;
        // update the icon
        // update the title
        remoteViews.setTextViewText(R.id.surah_name,name);
        // update the content

        // update the notification
        if (api < Build.VERSION_CODES.HONEYCOMB) {
            notificationManager.notify(0, builder.build());
        }else if (api >= Build.VERSION_CODES.HONEYCOMB) {
            notificationManager.notify(0, builder.build());
        }
    }
    static int time;
    public static void updatePlayButtonNotification(){

        int api = Build.VERSION.SDK_INT;

        // update the icon
        // update the title
        //puase
        if(Constants.isPlaying==true){
            time=ListenSrvicesManager.getInstance().getCurrentPosition();

            remoteViews.setImageViewResource(R.id.not_play,R.drawable.not_puase);
            ListenSrvicesManager.getInstance().pause();
            Constants.isPlaying=false;

        }else{
            remoteViews.setImageViewResource(R.id.not_play,R.drawable.not_playing);
            Constants.isPlaying=true;
            ListenSrvicesManager.getInstance().seekTo(time);
            ListenSrvicesManager.getInstance().start();

        }
        // update the content

        // update the notification
        if (api < Build.VERSION_CODES.HONEYCOMB) {
            notificationManager.notify(0, builder.build());
        }else if (api >= Build.VERSION_CODES.HONEYCOMB) {
            notificationManager.notify(0, builder.build());
        }
    }

    public  static  void cancelNotification() {
        notificationManager.cancelAll();
    }

}
