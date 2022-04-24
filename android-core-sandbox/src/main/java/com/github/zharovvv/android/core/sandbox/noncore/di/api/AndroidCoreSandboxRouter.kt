package com.github.zharovvv.android.core.sandbox.noncore.di.api

import android.content.Context
import com.github.zharovvv.common.di.meta.ProvidedBy

@ProvidedBy(AndroidCoreSandboxApi::class)
interface AndroidCoreSandboxRouter {

    fun launch(context: Context)
}