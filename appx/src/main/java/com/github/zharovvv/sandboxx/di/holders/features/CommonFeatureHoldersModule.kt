package com.github.zharovvv.sandboxx.di.holders.features

import android.app.Application
import android.content.Context
import com.github.zharovvv.android.core.sandbox.noncore.di.AndroidCoreSandboxFeatureHolderModule
import com.github.zharovvv.animationsandbox.di.AnimationSandboxFeatureHolderModule
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.common.di.FeatureContainerManager
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.compose.sandbox.di.ComposeSandboxFeatureHolderModule
import com.github.zharovvv.rxjavasandbox.di.RxJavaSandboxFeatureHolderModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        //Здесь указываем модули (featureHolders) фич, которые хотим подключить к приложению
        AndroidCoreSandboxFeatureHolderModule::class,
        RxJavaSandboxFeatureHolderModule::class,
        AnimationSandboxFeatureHolderModule::class,
        ComposeSandboxFeatureHolderModule::class
    ]
)
abstract class CommonFeatureHoldersModule {

    @Singleton
    @Binds
    abstract fun bindFeatureContainer(featureContainerManager: FeatureContainerManager): FeatureContainer

    @ApplicationContext
    @Singleton
    @Binds
    abstract fun bindApplicationContext(application: Application): Context

    companion object {
//        @Singleton
//        @Provides
//        @[IntoMap FeatureApiKey(ExampleFeatureApi::class)]
//        fun provideFeatureHolder(featureContainer: FeatureContainer, appContext: Context): FeatureHolder<FeatureApi> {
//            return ExampleFeatureHolder(featureContainer, appContext)
//        }
    }
}