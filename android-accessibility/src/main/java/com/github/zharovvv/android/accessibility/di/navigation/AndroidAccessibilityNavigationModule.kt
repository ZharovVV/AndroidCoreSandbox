package com.github.zharovvv.android.accessibility.di.navigation

import com.github.zharovvv.android.accessibility.R
import com.github.zharovvv.android.accessibility.di.api.AndroidAccessibilityApi
import com.github.zharovvv.android.accessibility.presentation.AndroidAccessibilityActivity
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class AndroidAccessibilityNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidAccessibilityApi::class)]
    fun androidAccessibilityEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Android Accessibility",
            description = "Сервисы специальных возможностей",
            iconResId = R.drawable.ic_baseline_accessibility_new_24,
            launcher = { context ->
                AndroidAccessibilityActivity.createIntent(context)
                    .let(context::startActivity)
            }
        )
}