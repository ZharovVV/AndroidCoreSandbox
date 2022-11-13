package com.github.zharovvv.android.accessibility.di.api

import com.github.zharovvv.android.accessibility.di.navigation.AndroidAccessibilityLauncher
import com.github.zharovvv.android.accessibility.di.navigation.AndroidAccessibilityLauncherImpl
import com.github.zharovvv.common.di.scope.PerFeature
import dagger.Module
import dagger.Provides

@Module
class AndroidAccessibilityModule {

    @PerFeature
    @Provides
    fun router(): AndroidAccessibilityLauncher = AndroidAccessibilityLauncherImpl()
}