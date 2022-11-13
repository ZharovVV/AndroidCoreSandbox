package com.github.zharovvv.android.core.sandbox.noncore.di.navigation

import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.meta.ProvidedBy
import com.github.zharovvv.core.navigation.ActivityLauncher

@ProvidedBy(AndroidCoreSandboxApi::class)
interface AndroidCoreSandboxLauncher : ActivityLauncher