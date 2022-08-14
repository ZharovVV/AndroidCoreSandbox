package com.github.zharovvv.rxjavasandbox.di.navigation

import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.rxjavasandbox.R
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class RxJavaSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(RxJavaSandboxApi::class)]
    fun rxJavaSandboxEntryPoint(): EntryPoint =
        EntryPoint(
            name = "RxJava Sandbox",
            description = "Описание работы RxJava 2",
            iconResId = R.drawable.rx_java_logo,
            routerProvider = { featureApi<RxJavaSandboxApi>().router }
        )
}