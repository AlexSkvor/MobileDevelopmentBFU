package ru.alexskvortsov.lab1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.Nullable;

public class MainActivityJava extends LoggingLifecycleActivity {

    private static final String channelId = "just_random_string_395";
    private static final int notifyId = 395;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendNotification();
    }

    private void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel();
        Notification notification = buildNotification();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(notifyId);
            manager.notify(notifyId, notification);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String name = "Notification channel name";
        NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Notification channel description");
        if (manager != null)
            manager.createNotificationChannel(channel);
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = notificationBuilder();
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Заголовок")
                .setSubText("Описание сути уведомления")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Очень длинный текст...\nРеально очень длинный. \n Целых три строчки!"))
                .setPriority(NotificationCompat.PRIORITY_MAX);
        return builder.build();
    }

    private NotificationCompat.Builder notificationBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return new NotificationCompat.Builder(this, channelId);
        else return new NotificationCompat.Builder(this, channelId);
    }
}
