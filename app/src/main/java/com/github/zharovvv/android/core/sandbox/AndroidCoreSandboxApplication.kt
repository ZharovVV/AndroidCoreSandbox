package com.github.zharovvv.android.core.sandbox

import android.app.Application
import android.util.Log
import com.github.zharovvv.android.core.sandbox.notification.NotificationUtil
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabaseProvider

class AndroidCoreSandboxApplication : Application() {

    companion object {
        lateinit var personDatabase: PersonDatabase

        @Suppress("ObjectPropertyName")
        private lateinit var _notificationUtil: NotificationUtil
        val notificationUtil: NotificationUtil
            get() {
                return _notificationUtil
            }
        private const val LOG_TAG = "ApplicationLifecycle"
    }

    /**
     * Вызывается при запуске приложения до создания каких-либо действий,
     * служб или объектов-получателей (за исключением поставщиков контента).
     */
    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "AndroidCoreSandboxApplication#onCreate")
        personDatabase = PersonDatabaseProvider.getPersonDatabase(this)
        _notificationUtil = NotificationUtil(this).apply { createNotificationChannel() }
    }
}