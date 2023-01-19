package com.github.zharovvv.android.core.sandbox.custom.view

import android.os.Bundle
import com.github.zharovvv.android.core.sandbox.databinding.ActivityCustomViewExampleBinding
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class CustomViewExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var binding: ActivityCustomViewExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewExampleBinding.inflate(layoutInflater)  //ViewBinding.inflate() = LayoutInflater.inflate() + ViewBinding.bind()
        setContentView(binding.root)
    }
}