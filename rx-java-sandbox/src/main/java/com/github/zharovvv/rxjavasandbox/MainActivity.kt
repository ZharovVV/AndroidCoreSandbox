package com.github.zharovvv.rxjavasandbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi
import com.github.zharovvv.rxjavasandbox.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_rx_java_sandbox)
        if (savedInstanceState == null) {
            mainFragment = MainFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, mainFragment, "mainFragment")
                .commit()
        } else {
            mainFragment = supportFragmentManager.findFragmentByTag("mainFragment") as MainFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseFeature<RxJavaSandboxApi>()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}