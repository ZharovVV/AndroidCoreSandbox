package com.github.zharovvv.android.accessibility.di.navigation

import com.github.zharovvv.android.accessibility.di.api.AndroidAccessibilityApi
import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.ActivityLauncher

@ProvidedBy(AndroidAccessibilityApi::class)
interface AndroidAccessibilityLauncher : ActivityLauncher