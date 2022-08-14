package com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        MainScreenEntryPointsModule::class
    ]
)
@Singleton
interface MainScreenEntryPointsComponent : MainScreenEntryPointsApi {
}