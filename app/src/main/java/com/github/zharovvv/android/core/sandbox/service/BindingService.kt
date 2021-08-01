package com.github.zharovvv.android.core.sandbox.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

/**
 * [PendingIntentService] и [BroadcastReceiverService] - примеры __асинхронного взаимодействия__
 * с сервисом. Т.е. мы отправляли запрос через startService,
 * а ответ нам приходил когда-нибудь потом посредством PendingIntent или BroadcastReceiver.
 *
 * Но есть и __синхронный способ__ взаимодействия с сервисом.
 * Он достигается с помощью биндинга.
 * Мы подключаемся к сервису и можем взаимодействовать с ним путем обычного вызова методов
 * с передачей данных и получением результатов.
 */
class BindingService : Service() {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private var updateListener: ((data: String) -> Unit)? = null

    inner class LocalBinder : Binder() {
        fun addUpdateListener(updateListener: (data: String) -> Unit) {
            this@BindingService.updateListener = updateListener
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("ServiceLifecycle", "$this#onCreate")
    }

    /**
     * Обычно реализуют либо onBind, либо onStartCommand.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("ServiceLifecycle", "$this#onStartCommand; startId: $startId")
        Thread {
            handler.post {
                updateListener?.invoke("Starting BindingService task")
            }
            someTask()
            handler.post {
                updateListener?.invoke("Finish BindingService task")
            }
            stopSelf()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Если мы биндингом запустили сервис, он будет жить, пока живет соединение.
     * Как только мы отключаемся, сервис уничтожается.
     */
    override fun onBind(intent: Intent?): IBinder {
        Log.i("ServiceLifecycle", "$this#onBind")
        return LocalBinder()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("ServiceLifecycle", "$this#onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("ServiceLifecycle", "$this#onUnbind")
        handler.post {
            updateListener?.invoke("Unbind BindingService")
            updateListener = null
        }
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ServiceLifecycle", "$this#onDestroy")
    }

    private fun someTask() {
        for (i in 1..30) {
            Thread.sleep(1000L)
            Log.i("ServiceLifecycle", "${Thread.currentThread().name} : $i")
            handler.post {
                updateListener?.invoke("output: $i")
            }
        }
    }
}