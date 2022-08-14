package com.github.zharovvv.compose.sandbox.di.api.navigation

import android.content.Context
import com.github.zharovvv.compose.sandbox.ui.ComposeMainActivity

class ComposeSandboxRouterImpl : ComposeSandboxRouter {

    override fun launch(context: Context) {
        context.startActivity(ComposeMainActivity.newIntent(context))
    }
}