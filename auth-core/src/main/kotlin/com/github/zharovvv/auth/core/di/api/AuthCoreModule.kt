package com.github.zharovvv.auth.core.di.api

import com.github.zharovvv.auth.core.di.api.internal.AuthCoreInternalModule
import dagger.Module

@Module(
    includes = [
        AuthCoreInternalModule::class
    ]
)
class AuthCoreModule {

}
