package com.github.zharovvv.android.core.sandbox.noncore.di.api

import android.content.Context
import com.github.zharovvv.android.core.sandbox.di.AppComponentLegacy
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AndroidCoreSandboxModule::class
    ],
    dependencies = [
        RxJavaSandboxApi::class,
        //Legacy
        AppComponentLegacy::class
    ]
)
@PerFeature
interface AndroidCoreSandboxComponent : AndroidCoreSandboxApi {

    @Component.Factory
    interface Factory {

        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context,
            rxJavaSandboxApi: RxJavaSandboxApi,
            appComponentLegacy: AppComponentLegacy
        ): AndroidCoreSandboxComponent
    }
}