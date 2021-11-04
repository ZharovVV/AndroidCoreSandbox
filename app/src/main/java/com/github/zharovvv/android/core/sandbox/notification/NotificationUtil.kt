package com.github.zharovvv.android.core.sandbox.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.zharovvv.android.core.sandbox.R

const val WORK_NOTIFICATION_ID = 1
const val WORK_CHANNEL_ID = "INFO_CHANNEL_ID"

fun makeNotification(message: String, context: Context) {
    //Это безопасно вызывать повторно, потому что создание существующего канала уведомлений не выполняет никаких действий.
    //Но лучше создавать канал при запуске приложения.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "INFO_WORK_CHANNEL"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(WORK_CHANNEL_ID, name, importance)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }
    //

    val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationCompat.Builder(context, WORK_CHANNEL_ID)
    } else {
        NotificationCompat.Builder(context)
    }

    notificationBuilder
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("WorkManager")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    NotificationManagerCompat.from(context)
        .notify(WORK_NOTIFICATION_ID, notificationBuilder.build())
}

fun makeNotificationAutoDismiss(message: String, context: Context) {
    makeNotification(message, context)
    Handler(context.mainLooper).postDelayed({
        NotificationManagerCompat.from(context).cancel(WORK_NOTIFICATION_ID)
    }, 3000L)
}