package com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api

import com.github.zharovvv.android.accessibility.di.navigation.AndroidAccessibilityNavigationModule
import com.github.zharovvv.android.core.sandbox.noncore.di.navigation.AndroidCoreSandboxNavigationModule
import com.github.zharovvv.animationsandbox.di.navigation.AndroidAnimationNavigationModule
import com.github.zharovvv.common.di.meta.FeatureApi
import com.github.zharovvv.compose.sandbox.di.api.navigation.ComposeSandboxNavigationModule
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxNavigationModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module(
    includes = [
        //здесь указываем модули, в которых объевлены фичевые entryPoint-ы, которые мы хотим увидеть
        //на главном экране
        AndroidCoreSandboxNavigationModule::class,
        AndroidAnimationNavigationModule::class,
        RxJavaSandboxNavigationModule::class,
        ComposeSandboxNavigationModule::class,
        AndroidAccessibilityNavigationModule::class
    ]
)
class MainScreenEntryPointsModule {

    @Singleton
    @Provides
    fun entryPointsMap(
        source: Map<Class<out FeatureApi>, @JvmSuppressWildcards EntryPoint>
    ): Map<KClass<out FeatureApi>, EntryPoint> {
        return source.mapKeys { it.key.kotlin }
    }
}