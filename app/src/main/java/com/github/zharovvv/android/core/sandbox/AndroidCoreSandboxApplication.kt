package com.github.zharovvv.android.core.sandbox

import android.app.Application
import android.content.Context
import android.util.Log
import com.github.zharovvv.android.core.sandbox.di.AppComponent
import com.github.zharovvv.android.core.sandbox.di.DaggerAppComponent
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

    lateinit var appComponent: AppComponent

    /**
     * Вызывается при запуске приложения до создания каких-либо действий,
     * служб или объектов-получателей (за исключением поставщиков контента).
     */
    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "AndroidCoreSandboxApplication#onCreate")
        appComponent = DaggerAppComponent.create()
        personDatabase = PersonDatabaseProvider.getPersonDatabase(this)
        _notificationUtil = NotificationUtil(this).apply { createNotificationChannel() }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is AndroidCoreSandboxApplication -> appComponent
        else -> this.applicationContext.appComponent
    }