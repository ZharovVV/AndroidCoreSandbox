package com.github.zharovvv.android.core.sandbox

import android.app.Application
import android.content.Context
import android.util.Log
import com.github.zharovvv.android.core.sandbox.di.AppComponentLegacy
import com.github.zharovvv.android.core.sandbox.di.AppDependencies
import com.github.zharovvv.android.core.sandbox.di.DaggerAppComponentLegacy
import com.github.zharovvv.android.core.sandbox.di.example.DependencyExample
import com.github.zharovvv.android.core.sandbox.notification.NotificationUtil
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabaseProvider

//Оставлено здесь на память
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

    lateinit var appComponentLegacy: AppComponentLegacy

    /**
     * Вызывается при запуске приложения до создания каких-либо действий,
     * служб или объектов-получателей (за исключением поставщиков контента).
     */
    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "AndroidCoreSandboxApplication#onCreate")
//        appComponent = DaggerAppComponent.create()
        appComponentLegacy = DaggerAppComponentLegacy.builder()
            .withContext(context = this)
            .withAppDependencies(object : AppDependencies {
                private val source = DependencyExample(
                    data = "dependencyExampleData"
                )
                override val dependencyExample: DependencyExample
                    get() = source

            })
            .build()
        personDatabase = PersonDatabaseProvider.getPersonDatabase(this)
        _notificationUtil = NotificationUtil(this).apply { createNotificationChannel() }
    }
}

val Context.appComponentLegacy: AppComponentLegacy
    get() = when (this) {
        is AndroidCoreSandboxApplication -> appComponentLegacy
        else -> this.applicationContext.appComponentLegacy
    }