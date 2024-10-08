package com.example.d308.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.d308.R;

public class MyVacationReceiver extends BroadcastReceiver {

    String channel_id = "test";

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO: This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        int notificationId = intent.getIntExtra("notification_id", 0);
        String message = intent.getStringExtra("key");
        Toast.makeText(context, intent.getStringExtra("key") , Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentTitle("Vacation Alert!").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name2);
        String description = context.getString(R.string.channel_description2);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}