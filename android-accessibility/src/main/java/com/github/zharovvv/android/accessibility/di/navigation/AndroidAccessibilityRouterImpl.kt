package com.github.zharovvv.android.accessibility.di.navigation

import android.content.Context
import com.github.zharovvv.android.accessibility.presentation.AndroidAccessibilityActivity

internal class AndroidAccessibilityRouterImpl : AndroidAccessibilityRouter {

    override fun launch(context: Context) {
        AndroidAccessibilityActivity.createIntent(context)
            .let(context::startActivity)
    }
}