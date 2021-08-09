package com.github.zharovvv.android.core.sandbox

import android.app.Application
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDbOpenHelper

class AndroidCoreSandboxApplication : Application() {

    companion object {
        lateinit var personDatabase: PersonDatabase
    }

    override fun onCreate() {
        super.onCreate()
        personDatabase = PersonDatabase(PersonDbOpenHelper(this, 1))
    }
}