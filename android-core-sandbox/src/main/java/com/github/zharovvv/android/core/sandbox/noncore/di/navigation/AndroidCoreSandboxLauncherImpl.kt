package com.github.zharovvv.android.core.sandbox.noncore.di.navigation

import android.content.Context
import com.github.zharovvv.android.core.sandbox.TrueMainActivity

class AndroidCoreSandboxLauncherImpl : AndroidCoreSandboxLauncher {

    override fun launch(context: Context) {
        context.startActivity(TrueMainActivity.newIntent(context))
    }
}