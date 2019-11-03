package com.example.mainuddin.iot;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.suke.widget.SwitchButton;

import static com.example.mainuddin.iot.Delegate.theMainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /* There are two types of messages data messages and notification messages. Data messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app is in the foreground. When the app is in the background an automatically generated notification is displayed. */
        String notificationTitle = null, notificationBody = null;
        String dataTitle = null, dataMessage = null, isFire = "0";
        Log.d(TAG, "Message Notification got: ");
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("title").toString() + remoteMessage.getData().get("msg").toString());
            dataTitle = remoteMessage.getData().get("title").toString();
            dataMessage = remoteMessage.getData().get("msg").toString();

            isFire = remoteMessage.getData().get("fire").toString();
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        if(isFire.equals("1"))sendNotification(dataTitle, dataMessage);


    }


    private void sendNotification(final String dataTitle, final String dataMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", dataTitle);
        intent.putExtra("message", dataMessage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //alarm.mp3");
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_stat_round)
                .setContentTitle(dataTitle)
                .setContentText(dataMessage)
                .setAutoCancel(true)
                .setSound(Uri.parse("android.resource://"
                        + "com.example.mainuddin.iot"+ "/"
                        + R.raw.alarm))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        try{

            Intent i=new Intent(theMainActivity,dialogue.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Log.d("hhh",i.toString());
            theMainActivity.startActivity(i);
        }catch(Exception  exception){
            Intent i=new Intent(this,dialogue.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Log.d("hhh",i.toString());
            startActivity(i);
        }



    }
}




