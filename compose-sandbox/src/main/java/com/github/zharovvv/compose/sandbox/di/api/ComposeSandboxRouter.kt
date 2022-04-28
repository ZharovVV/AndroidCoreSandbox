package com.github.zharovvv.compose.sandbox.di.api

import android.content.Context
import com.github.zharovvv.common.di.meta.ProvidedBy

@ProvidedBy(ComposeSandboxApi::class)
interface ComposeSandboxRouter {

    fun launch(context: Context)
}