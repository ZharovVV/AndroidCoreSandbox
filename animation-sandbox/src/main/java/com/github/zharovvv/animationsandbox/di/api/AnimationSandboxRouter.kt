package com.github.zharovvv.animationsandbox.di.api

import android.content.Context
import com.github.zharovvv.common.di.meta.ProvidedBy

@ProvidedBy(AnimationSandboxApi::class)
interface AnimationSandboxRouter {

    fun launch(context: Context)
}