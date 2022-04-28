package com.github.zharovvv.compose.sandbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi

class ComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello World!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseFeature<ComposeSandboxApi>()
        }
    }

    companion object {
        internal fun newIntent(context: Context): Intent =
            Intent(context, ComposeMainActivity::class.java)
    }
}