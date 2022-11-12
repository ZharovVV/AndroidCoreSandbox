package com.github.zharovvv.graphics.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.di.api.Graphics3DApi

class Graphics3DActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphics_3d)
    }

    override fun finish() {
        releaseFeature<Graphics3DApi>()
        super.finish()
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, Graphics3DActivity::class.java)
    }
}