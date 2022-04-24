package com.github.zharovvv.animationsandbox

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import com.github.zharovvv.animationsandbox.di.api.AnimationSandboxApi
import com.github.zharovvv.animationsandbox.fragments.MainFragment
import com.github.zharovvv.common.di.releaseFeature

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_sandbox_activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MainFragment(), "main_fragment")
                .commit()
        }
        //Не относится к анимации.
        //LoaderSandbox
        LoaderManager.getInstance(this)
            .initLoader(R.id.stub_loader_id, Bundle.EMPTY, StubLoaderCallback(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseFeature<AnimationSandboxApi>()
        }
    }

    companion object {
        internal fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}