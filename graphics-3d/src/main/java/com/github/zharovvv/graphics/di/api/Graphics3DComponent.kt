package com.github.zharovvv.graphics.di.api

import android.content.Context
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        Graphics3DModule::class
    ]
)
@PerFeature
internal interface Graphics3DComponent : Graphics3DInternalApi {

    @Component.Factory
    interface Factory {

        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context,
        ): Graphics3DComponent
    }
}