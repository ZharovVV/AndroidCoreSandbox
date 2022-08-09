package com.github.zharovvv.android.accessibility.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Component

@Component(
    modules = [
        AndroidAccessibilityModule::class
    ]
)
@PerFeature
interface AndroidAccessibilityComponent : AndroidAccessibilityApi {

}