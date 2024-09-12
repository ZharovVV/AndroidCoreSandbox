package com.github.zharovvv.android.core.sandbox.alarm.manager

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.github.zharovvv.android.core.sandbox.TrueMainActivity
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("AlarmReceiver", "AlarmReceiver#onReceive")
        //Android 10 (уровень API 29) и выше накладывают ограничения на то,
        // когда приложения могут начинать действия, когда приложение работает в фоновом режиме.
//        val startActivityIntent = Intent(context, TrueMainActivity::class.java)
//            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
//        context?.startActivity(startActivityIntent) // получаем E/ActivityTaskManager: Abort background activity starts from 10358
        val startActivityPendingIntent = PendingIntent.getActivities(   //Делаем искуственный back-stack activities.
            context,
            0,
            arrayOf(
                Intent(context, TrueMainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                },
                Intent(context, AlarmManagerExampleActivity::class.java)
            ),
            PendingIntent.FLAG_IMMUTABLE
        )
        intArrayOf(1, 3, 5)
        featureApi<AndroidCoreSandboxApi>().notificationUtil.sendNotification(
            notificationId = 3,
            title = "AlarmManager",
            text = "Alarm!",
            contentIntent = startActivityPendingIntent,
            autoCancel = true
        )
    }
}