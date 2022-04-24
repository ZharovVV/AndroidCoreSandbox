package com.github.zharovvv.android.core.sandbox.noncore.di.api

import com.github.zharovvv.android.core.sandbox.di.AppComponentLegacy
import com.github.zharovvv.android.core.sandbox.notification.NotificationUtil
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.common.di.meta.FeatureApi

interface AndroidCoreSandboxApi : FeatureApi {

    val router: AndroidCoreSandboxRouter

    val legacy: AppComponentLegacy

    val notificationUtil: NotificationUtil

    val personDatabase: PersonDatabase
}