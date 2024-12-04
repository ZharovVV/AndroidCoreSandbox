package com.github.zharovvv.auth.core.di.api

import android.content.Context
import com.github.zharovvv.auth.core.di.api.internal.AuthCoreInternalApi
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AuthCoreModule::class
    ]
)
@PerFeature
internal interface AuthCoreComponent : AuthCoreInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): AuthCoreComponent
    }
}