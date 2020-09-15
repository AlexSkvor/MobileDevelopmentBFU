package ru.alexskvortsov.lab1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class MainActivity : LoggingLifecycleActivity() {

    companion object {
        const val channelId = "just_random_string_395"
        const val notifyId = 395
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        sendNotification()
    }

    private fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel()
        val notification = buildNotification()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notifyId)
        manager.notify(notifyId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name = "Notification channel name"
        val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Notification channel description"
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        val builder = notificationBuilder
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Заголовок")
            .setSubText("Описание сути уведомления")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Очень длинный текст...\nРеально очень длинный. \n Целых три строчки!"))
            .priority = NotificationCompat.PRIORITY_MAX
        return builder.build()
    }

    private val notificationBuilder
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationCompat.Builder(this, channelId)
        else NotificationCompat.Builder(this, channelId)
}
