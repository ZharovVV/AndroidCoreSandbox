package com.github.zharovvv.android.core.sandbox.custom.view

import android.os.Bundle
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.databinding.ActivityCustomViewExampleBinding

class CustomViewExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var binding: ActivityCustomViewExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewExampleBinding.inflate(layoutInflater)  //ViewBinding.inflate() = LayoutInflater.inflate() + ViewBinding.bind()
        setContentView(binding.root)
    }
}