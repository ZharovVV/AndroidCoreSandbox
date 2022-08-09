package com.github.zharovvv.android.accessibility.di.api

import android.content.Context
import com.github.zharovvv.common.di.meta.ProvidedBy

@ProvidedBy(AndroidAccessibilityApi::class)
interface AndroidAccessibilityRouter {

    fun launch(context: Context)
}