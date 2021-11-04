package com.github.zharovvv.android.core.sandbox.alarm.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.github.zharovvv.android.core.sandbox.notification.makeNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("AlarmReceiver", "AlarmReceiver#onReceive")
        //Android 10 (уровень API 29) и выше накладывают ограничения на то,
        // когда приложения могут начинать действия, когда приложение работает в фоновом режиме.
//        val startActivityIntent = Intent(context, TrueMainActivity::class.java)
//            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
//        context?.startActivity(startActivityIntent) // получаем E/ActivityTaskManager: Abort background activity starts from 10358
        makeNotification(message = "Alarm", context = context!!)
    }
}