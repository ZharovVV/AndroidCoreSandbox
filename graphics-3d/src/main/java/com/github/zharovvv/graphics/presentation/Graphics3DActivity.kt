package com.github.zharovvv.graphics.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.graphics.R
import com.github.zharovvv.graphics.di.api.Graphics3DApi
import com.github.zharovvv.graphics.presentation.fragments.MainFragment

class Graphics3DActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphics_3d)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.host, MainFragment.TAG)
            }
        }
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