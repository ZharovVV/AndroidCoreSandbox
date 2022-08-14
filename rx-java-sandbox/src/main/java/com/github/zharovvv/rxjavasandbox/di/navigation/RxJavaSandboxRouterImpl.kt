package com.github.zharovvv.rxjavasandbox.di.navigation

import android.content.Context
import com.github.zharovvv.rxjavasandbox.MainActivity

class RxJavaSandboxRouterImpl : RxJavaSandboxRouter {

    override fun launch(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}