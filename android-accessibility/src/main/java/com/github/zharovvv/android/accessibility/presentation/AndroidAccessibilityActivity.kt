package com.github.zharovvv.android.accessibility.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.accessibility.R
import com.github.zharovvv.android.accessibility.di.api.AndroidAccessibilityApi
import com.github.zharovvv.common.di.releaseFeature

class AndroidAccessibilityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_accessibility)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseFeature<AndroidAccessibilityApi>()
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, AndroidAccessibilityActivity::class.java)
    }
}