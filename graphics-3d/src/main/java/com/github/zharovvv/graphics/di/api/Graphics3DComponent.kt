package com.github.zharovvv.graphics.di.api

import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.graphics.di.internal.Graphics3DInternalApi
import dagger.Component

@Component(
    modules = [
        Graphics3DModule::class
    ]
)
@PerFeature
internal interface Graphics3DComponent : Graphics3DInternalApi