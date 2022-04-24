package com.github.zharovvv.sandboxx

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.rxjavasandbox.di.api.RxJavaSandboxApi

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toAndroidCoreApiButton: Button = findViewById(R.id.to_android_core_sandbox_module)
        toAndroidCoreApiButton.setOnClickListener {
            featureApi<AndroidCoreSandboxApi>().router.launch(this)
        }
        val toRxJavaModuleButton: Button = findViewById(R.id.to_rx_java_module_button)
        toRxJavaModuleButton.setOnClickListener {
            featureApi<RxJavaSandboxApi>().router.launch(this)
        }
    }
}