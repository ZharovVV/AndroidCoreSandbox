package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Component

@Component(
    modules = [
        Graphics3DModule::class
    ]
)
@PerFeature
interface Graphics3DComponent : Graphics3DApi {

}