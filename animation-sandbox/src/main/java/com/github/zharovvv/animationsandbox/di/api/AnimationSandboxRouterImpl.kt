package com.github.zharovvv.animationsandbox.di.api

import android.content.Context
import com.github.zharovvv.animationsandbox.MainActivity

class AnimationSandboxRouterImpl : AnimationSandboxRouter {

    override fun launch(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}