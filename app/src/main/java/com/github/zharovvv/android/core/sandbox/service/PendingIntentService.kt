package com.github.zharovvv.android.core.sandbox.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.github.zharovvv.android.core.sandbox.service.ServiceExampleActivity.Companion.PENDING_INTENT_EXTRA_NAME

class PendingIntentService : Service() {

    companion object {
        const val SERVICE_EXTRA_NAME = "SERVICE_EXTRA_NAME"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pendingIntent = intent?.getParcelableExtra<PendingIntent>(PENDING_INTENT_EXTRA_NAME)
        pendingIntent?.send(
            this,
            ServiceExampleActivity.START_SERVICE_RESULT_CODE,
            Intent().putExtra(SERVICE_EXTRA_NAME, "Start PendingIntentService")
        )
        Thread {
            someTask(pendingIntent)
            pendingIntent?.send(
                this,
                ServiceExampleActivity.FINISH_SERVICE_RESULT_CODE,
                Intent().putExtra(SERVICE_EXTRA_NAME, "Finish PendingIntentService")
            )
            stopSelf()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun someTask(pendingIntent: PendingIntent?) {
        for (i in 1..20) {
            Thread.sleep(1000L)
            Log.i("ServiceLifecycle", "${Thread.currentThread().name} : $i")
            pendingIntent?.send(
                this,
                ServiceExampleActivity.UPDATE_SERVICE_RESULT_CODE,
                Intent().putExtra(SERVICE_EXTRA_NAME, "Output: $i")
            )
        }
    }
}