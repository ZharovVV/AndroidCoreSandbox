package com.github.zharovvv.android.core.sandbox.noncore.di.navigation

import android.content.Context
import com.github.zharovvv.android.core.sandbox.TrueMainActivity

class AndroidCoreSandboxRouterImpl : AndroidCoreSandboxRouter {

    override fun launch(context: Context) {
        context.startActivity(TrueMainActivity.newIntent(context))
    }
}