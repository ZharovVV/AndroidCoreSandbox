package com.github.zharovvv.animationsandbox

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import com.github.zharovvv.animationsandbox.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}