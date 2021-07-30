package com.github.zharovvv.android.core.sandbox.service

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.SERVICE_INPUT
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.START_SERVICE_CONSTANT

class ServiceExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_service_example_activity) {

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var startServiceConstantSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startButton = findViewById(R.id.start_service)
        stopButton = findViewById(R.id.stop_service)
        startServiceConstantSpinner = findViewById(R.id.start_service_constant_spinner)
        startServiceConstantSpinner.apply {
            val arrayAdapter = ArrayAdapter<String>(
                    this@ServiceExampleActivity,
                    android.R.layout.simple_spinner_item,
                    listOf("START_NOT_STICKY", "START_STICKY", "START_STICKY_COMPATIBILITY", "START_REDELIVER_INTENT")
            )
            adapter = arrayAdapter
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            prompt = "START_SERVICE_CONSTANT"
            setSelection(3)
        }
        startButton.setOnClickListener {
            startService<ExampleService> { intent ->
                intent.putExtra(START_SERVICE_CONSTANT, startServiceConstantSpinner.selectedItem as String)
                intent.putExtra(SERVICE_INPUT, "some data")
            }
        }
        stopButton.setOnClickListener {
            stopService<ExampleService>()
        }
    }
}