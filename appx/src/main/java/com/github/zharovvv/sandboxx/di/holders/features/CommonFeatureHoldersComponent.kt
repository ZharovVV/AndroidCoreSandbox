package com.github.zharovvv.sandboxx.di.holders.features

import android.app.Application
import com.github.zharovvv.common.di.FeatureContainerManager
import com.github.zharovvv.common.di.FeatureHolder
import com.github.zharovvv.common.di.meta.FeatureApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CommonFeatureHoldersModule::class]
)
interface CommonFeatureHoldersComponent {

    //Если использовать KClass в качестве ключа, то у даггера возникают проблемы
    fun getFeatureHolders(): Map<Class<out FeatureApi>, @JvmSuppressWildcards FeatureHolder<FeatureApi>>

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance featureContainerManager: FeatureContainerManager
        ): CommonFeatureHoldersComponent
    }
}