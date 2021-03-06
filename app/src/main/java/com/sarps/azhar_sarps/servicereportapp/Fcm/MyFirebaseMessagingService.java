package com.sarps.azhar_sarps.servicereportapp.Fcm;

/**
 * Created by azhar-sarps on 11-Jun-17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Dealer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String data;
    String msg;
    Bitmap remote_picture=null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.v(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.v(TAG, "Message data payload:- " + remoteMessage.getData());
            showNotification(data.get("message"),data.get("unique_string"),data.get("subject"));
//            if (data.get("image_url").isEmpty()) {
//                showNotification(data.get("message"), data.get("url"));
//            } else {
//                showNotification_withImage(data.get("message"), "https://cash2kart.com/upload/app_deals/" + data.get("image_url"), data.get("url"));
//            }


//            System.out.println("https://cash2kart.com/upload/app_deals/" + data.get("image_url"));

        }

        if (remoteMessage.getNotification() != null) {
            Log.v(TAG, "Message Notification Body:- " + remoteMessage.getNotification().getBody());
            System.out.println("Message :- " + remoteMessage.getNotification().getBody());
        }

    }


    private void showNotification(String message,String unique_string, String subject) {


        if (!TextUtils.isEmpty(message)) {

            if(unique_string.equals("dealer")){
                Intent intent = new Intent(this, MainActivity_Dealer.class);
                intent.putExtra("subject",subject);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_santech_transparent)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.logo))
                        .setContentTitle("Service Report App")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            } else if(unique_string.equals("engineer")){

                Intent intent = new Intent(this, MainActivity_Engineers.class);
                intent.putExtra("subject",subject);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_santech_transparent)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.logo))
                        .setContentTitle("Service Report App")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }else if(unique_string.equals("cust")) {


                Intent intent = new Intent(this, MainActivity_Customer.class);
                intent.putExtra("subject",subject);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_santech_transparent)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.logo))
                        .setContentTitle("Service Report App")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());

            }
//            else if(unique_string.equals("super")) {
//
//                Intent intent = new Intent(this, MainActivity_Engineers.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.logo)
//                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.logo))
//                        .setContentTitle("Service Report App")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle())
//                        .setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                        .setContentIntent(pendingIntent);
//
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(0, notificationBuilder.build());
//            }
//            if(unique_string_sec.equals("super")){
//                Intent intent = new Intent(this, MainActivity_Engineers.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.logo)
//                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.logo))
//                        .setContentTitle("Service Report App")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle())
//                        .setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                        .setContentIntent(pendingIntent);
//
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(0, notificationBuilder.build());
//            }
        }
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}

