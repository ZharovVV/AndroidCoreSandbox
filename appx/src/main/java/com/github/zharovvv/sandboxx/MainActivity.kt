package com.github.zharovvv.sandboxx

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button: Button = findViewById(R.id.main_activity_button)
        button.setOnClickListener {
            featureApi<RxJavaSandboxApi>().router.launch(this)
        }
    }
}