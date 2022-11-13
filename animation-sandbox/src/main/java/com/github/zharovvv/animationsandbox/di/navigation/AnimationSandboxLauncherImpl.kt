package com.github.zharovvv.animationsandbox.di.navigation

import android.content.Context
import com.github.zharovvv.animationsandbox.MainActivity

class AnimationSandboxLauncherImpl : AnimationSandboxLauncher {

    override fun launch(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}