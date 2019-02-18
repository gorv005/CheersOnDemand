package com.cheersondemand.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by AB on 6/20/2018.
 */

public class FirebaseMessagingServices extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
        SharedPreference.getInstance(this).setString(C.DEVICE_TOKEN,s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Create and show notification
        Log.e(" remoessage.getData()=",""+remoteMessage.getData());

        Log.e("remoteMessage=",""+remoteMessage);
     //   sendNotification(remoteMessage);
        showNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String messageBody=remoteMessage.getData().get("message");
        Log.e("messageBody=",""+remoteMessage.getData().get("message"));
        Log.e("OrderId=",""+remoteMessage.getData().get("resource"));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(C.ORDER_ID, remoteMessage.getData().get("resource"));
        intent.putExtra(C.IS_NOTIFICATION, true);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.noti_icon)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Util.getID() /* ID of notification */, notificationBuilder.build());
    }


    void showNotification(RemoteMessage remoteMessage){
        try {
            String messageBody=remoteMessage.getData().get("message");
           // String name = "Cheers On Demand";
          //  String id = "Cancel Ride"; // The user-visible name of the channel.
            String description = messageBody;
            NotificationCompat.Builder builder;
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(getString(R.string.app_name));
                if (mChannel == null) {
                    mChannel = new NotificationChannel(getString(R.string.app_name), getString(R.string.app_name), importance);
                    mChannel.setDescription(description);
                    mChannel.enableVibration(true);
                    mChannel.setLightColor(Color.GREEN);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);
                }

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(C.ORDER_ID, remoteMessage.getData().get("resource"));
                intent.putExtra(C.IS_NOTIFICATION, true);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder = new NotificationCompat.Builder(this, getString(R.string.app_name))
                        .setSmallIcon(R.drawable.noti_icon)
                        .setContentTitle(getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(C.ORDER_ID, remoteMessage.getData().get("resource"));
                intent.putExtra(C.IS_NOTIFICATION, true);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.noti_icon)
                        .setContentTitle(getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);

            }
            Notification notification = builder.build();
            notificationManager.notify(Util.getID(), notification);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
