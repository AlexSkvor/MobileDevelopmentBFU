package ru.alexskvortsov.lab1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.IBinder
import androidx.core.app.NotificationCompat
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class ServiceWithActivityStarting : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()

        makeForeground()

        Single.just(Unit)
            .delay(10, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess {
                startActivity(
                    Intent(this, MainActivity::class.java)
                    //.addFlags(FLAG_ACTIVITY_NEW_TASK)
                )
            }
            .subscribe()
    }

    private fun makeForeground() {
        val channel = NotificationChannel(
            "Lab1 channel",
            "test channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        notificationManager.createNotificationChannel(channel)


        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainActivityIntent,
            FLAG_CANCEL_CURRENT or FLAG_MUTABLE
        )

        val notification = NotificationCompat.Builder(this, channel.id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My Awesome App")
            .setContentText("Doing some work...")
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(12345, notification)
        startForeground(12345, notification)
    }
}