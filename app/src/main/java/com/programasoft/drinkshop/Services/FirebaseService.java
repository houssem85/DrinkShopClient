package com.programasoft.drinkshop.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.programasoft.drinkshop.R;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.Utils.NotificationHelper;
import com.programasoft.drinkshop.model.token;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        InsertUpdateToken(s);
    }


    private void InsertUpdateToken(String token){
        if(Common.CorrentUser!=null) {
            IDrinkShopApi api=Common.getApi();
            api.InsertUpdateToken(Common.CorrentUser.getPhone(),token,false).enqueue(new Callback<token>() {
                @Override
                public void onResponse(Call<token> call, Response<token> response) {

                }

                @Override
                public void onFailure(Call<token> call, Throwable t) {

                }
            });

        }


    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData()!=null)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            {
                sendNotificationApi26(remoteMessage);
            }else
                {
                    sendNotification(remoteMessage);

                }
        }
    }

    private void sendNotificationApi26(RemoteMessage remoteMessage) {
         // Notification channel in api 26
        // Get information from Message
        Map<String,String> hashMap=remoteMessage.getData();
        String title=hashMap.get("title");
        String message=hashMap.get("message");
        NotificationHelper helper;
        Notification.Builder builder;
        Uri DefaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        helper=new NotificationHelper(this);
        builder=helper.GetDrinkShopNotification(title,message,DefaultSoundUri);
        helper.getNotificationManager().notify(new Random().nextInt(),builder.build());

    }

    private void sendNotification(RemoteMessage remoteMessage) {

        // Get information from Message
        Map<String,String> hashMap=remoteMessage.getData();
        String title=hashMap.get("title");
        String message=hashMap.get("message");
        Uri DefaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this).
                 setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title).setContentText(message)
                .setAutoCancel(true)
                .setSound(DefaultSoundUri);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(),builder.build());

    }
}
