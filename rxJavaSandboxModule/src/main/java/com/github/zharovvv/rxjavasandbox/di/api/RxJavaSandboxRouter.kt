package com.github.zharovvv.rxjavasandbox.di.api

import android.content.Context
import com.github.zharovvv.common.di.meta.ProvidedBy

@ProvidedBy(RxJavaSandboxApi::class)
interface RxJavaSandboxRouter {

    fun launch(context: Context)
}