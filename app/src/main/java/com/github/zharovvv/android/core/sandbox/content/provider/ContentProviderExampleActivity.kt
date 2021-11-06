package com.github.zharovvv.android.core.sandbox.content.provider

import android.os.Bundle
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class ContentProviderExampleActivity : LogLifecycleAppCompatActivity() {

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_example)
    }
}