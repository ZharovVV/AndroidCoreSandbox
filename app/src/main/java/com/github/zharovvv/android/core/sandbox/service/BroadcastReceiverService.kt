package com.github.zharovvv.android.core.sandbox.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.github.zharovvv.android.core.sandbox.service.ServiceResponseBroadcastReceiver.Companion.BROADCAST_ACTION

class BroadcastReceiverService : Service() {

    companion object {
        const val BR_SERVICE_EXTRA_KEY_NAME = "BROADCAST_RECEIVER_SERVICE"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val brIntent = Intent(BROADCAST_ACTION)
        Thread {
            brIntent.putExtra(BR_SERVICE_EXTRA_KEY_NAME, "Start BroadcastReceiverService")
            sendBroadcast(brIntent)
            someTask(brIntent)
            brIntent.putExtra(BR_SERVICE_EXTRA_KEY_NAME, "Finish BroadcastReceiverService")
            sendBroadcast(brIntent)
            stopSelf()
        }.start()
        return super.onStartCommand(brIntent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun someTask(intent: Intent) {
        for (i in 1..30) {
            Thread.sleep(1000L)
            Log.i("ServiceLifecycle", "${Thread.currentThread().name} : $i")
            intent.putExtra(BR_SERVICE_EXTRA_KEY_NAME, "Output: $i")
            sendBroadcast(intent)
        }
    }
}