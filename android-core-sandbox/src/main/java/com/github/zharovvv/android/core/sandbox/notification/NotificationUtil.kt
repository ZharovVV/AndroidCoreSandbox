package com.github.zharovvv.android.core.sandbox.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.zharovvv.android.core.sandbox.R

class NotificationUtil(base: Context) : ContextWrapper(base) {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "ANDROID_CORE_SANDBOX_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "Основной канал уведомлений"
        private const val DEFAULT_NOTIFICATION_ID = 1
    }

    private val notificationManager =
        base.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    /**
     * Лучше создавать канал при запуске приложения.
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                importance
            )
            //Это безопасно вызывать повторно, потому что создание существующего канала уведомлений
            // не выполняет никаких действий.
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun sendNotification(
        notificationId: Int = DEFAULT_NOTIFICATION_ID,
        title: String,
        text: String,
        contentIntent: PendingIntent? = null,
        autoCancel: Boolean = false
    ) {
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(baseContext, NOTIFICATION_CHANNEL_ID)
        } else {
            NotificationCompat.Builder(baseContext)
        }
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(contentIntent)
            .setAutoCancel(autoCancel)

        NotificationManagerCompat.from(baseContext)
            .notify(notificationId, notificationBuilder.build())
    }
}