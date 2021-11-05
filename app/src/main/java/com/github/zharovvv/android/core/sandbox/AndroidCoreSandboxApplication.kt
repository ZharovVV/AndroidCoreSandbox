package com.github.zharovvv.android.core.sandbox

import android.app.Application
import com.github.zharovvv.android.core.sandbox.notification.NotificationUtil
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDbOpenHelper

class AndroidCoreSandboxApplication : Application() {

    companion object {
        lateinit var personDatabase: PersonDatabase
        @Suppress("ObjectPropertyName")
        private lateinit var _notificationUtil: NotificationUtil
        val notificationUtil: NotificationUtil get() {
            return _notificationUtil
        }
    }

    override fun onCreate() {
        super.onCreate()
        personDatabase = PersonDatabase(PersonDbOpenHelper(this, 1))
        _notificationUtil = NotificationUtil(this).apply { createNotificationChannel() }
    }
}