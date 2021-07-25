package com.github.zharovvv.android.core.sandbox

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class LogLifecycleAppCompatActivity : AppCompatActivity {

    constructor() : super()

    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ActivityLifeCycle", "$this#onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ActivityLifeCycle", "$this#onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ActivityLifeCycle", "$this#onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ActivityLifeCycle", "$this#onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ActivityLifeCycle", "$this#onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ActivityLifeCycle", "$this#onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ActivityLifeCycle", "$this#onDestroy")
    }

}