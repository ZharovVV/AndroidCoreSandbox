package com.github.zharovvv.compose.sandbox.di.api.navigation

import android.content.Context
import com.github.zharovvv.compose.sandbox.ui.ComposeMainActivity

class ComposeSandboxLauncherImpl : ComposeSandboxLauncher {

    override fun launch(context: Context) {
        context.startActivity(ComposeMainActivity.newIntent(context))
    }
}