package com.github.zharovvv.sandboxx

import android.app.Application
import com.github.zharovvv.sandboxx.startup.DiStartup

class SandboxXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DiStartup().create(this)
    }
}