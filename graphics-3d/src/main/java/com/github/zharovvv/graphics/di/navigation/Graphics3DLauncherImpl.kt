package com.github.zharovvv.graphics.di.navigation

import android.content.Context
import com.github.zharovvv.graphics.presentation.Graphics3DActivity

internal class Graphics3DLauncherImpl : Graphics3DLauncher {

    override fun launch(context: Context) {
        Graphics3DActivity.createIntent(context)
            .let(context::startActivity)
    }
}