package com.github.zharovvv.android.core.sandbox.noncore.di

import android.content.Context
import com.github.zharovvv.android.core.sandbox.di.AppDependencies
import com.github.zharovvv.android.core.sandbox.di.DaggerAppComponentLegacy
import com.github.zharovvv.android.core.sandbox.di.example.DependencyExample
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.android.core.sandbox.noncore.di.api.DaggerAndroidCoreSandboxComponent
import com.github.zharovvv.common.di.BaseFeatureHolder
import com.github.zharovvv.common.di.FeatureContainer
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi

class AndroidCoreSandboxSandboxFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<AndroidCoreSandboxApi>(featureContainer) {

    override fun buildFeature(): AndroidCoreSandboxApi {
        return DaggerAndroidCoreSandboxComponent.factory()
            .create(
                applicationContext = applicationContext,
                rxJavaSandboxApi = getDependency(RxJavaSandboxApi::class),
                appComponentLegacy = DaggerAppComponentLegacy.builder()
                    .withAppDependencies(
                        object : AppDependencies {
                            private val source = DependencyExample(
                                data = "dependencyExampleData"
                            )
                            override val dependencyExample: DependencyExample
                                get() = source
                        }
                    )
                    .withContext(applicationContext)
                    .build()
            )
    }

    override fun destroyDependencies() {
//        releaseDependency(RxJavaSandboxApi::class) не нужно, так как фича очищает сама себя
    }
}