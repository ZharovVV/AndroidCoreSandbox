package com.github.zharovvv.android.core.sandbox.service

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.SERVICE_INPUT
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.START_SERVICE_CONSTANT
import com.github.zharovvv.android.core.sandbox.service.PendingIntentService.Companion.SERVICE_EXTRA_NAME

class ServiceExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_service_example_activity) {

    companion object {
        const val TASK_REQUEST_CODE = 3
        const val PENDING_INTENT_EXTRA_NAME = "PENDING_INTENT"
        const val START_SERVICE_RESULT_CODE = 0
        const val UPDATE_SERVICE_RESULT_CODE = 1
        const val FINISH_SERVICE_RESULT_CODE = 2
    }

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var startServiceConstantSpinner: Spinner

    private lateinit var serviceResultOutputTextView: TextView
    private lateinit var startButtonPendingIntent: Button

    private lateinit var serviceResultOutputBrTextView: TextView
    private lateinit var startButtonBr: Button
    private lateinit var broadcastReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSimpleServicePart()
        initPendingIntentServicePart()
        initBroadcastReceiverServicePart()
    }

    private fun initSimpleServicePart() {
        startButton = findViewById(R.id.start_service)
        stopButton = findViewById(R.id.stop_service)
        startServiceConstantSpinner = findViewById(R.id.start_service_constant_spinner)
        startServiceConstantSpinner.apply {
            val arrayAdapter = ArrayAdapter<String>(
                this@ServiceExampleActivity,
                android.R.layout.simple_spinner_item,
                listOf(
                    "START_NOT_STICKY",
                    "START_STICKY",
                    "START_STICKY_COMPATIBILITY",
                    "START_REDELIVER_INTENT"
                )
            )
            adapter = arrayAdapter
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            prompt = "START_SERVICE_CONSTANT"
            setSelection(3)
        }
        startButton.setOnClickListener {
            startService<ExampleService> { intent ->
                intent.putExtra(
                    START_SERVICE_CONSTANT,
                    startServiceConstantSpinner.selectedItem as String
                )
                intent.putExtra(SERVICE_INPUT, "some data")
            }
        }
        stopButton.setOnClickListener {
            stopService<ExampleService>()
        }
    }

    /**
     * Использование PendingIntent для обратной связи от сервиса.
     */
    private fun initPendingIntentServicePart() {
        serviceResultOutputTextView = findViewById(R.id.service_result_output_text_view)
        startButtonPendingIntent = findViewById(R.id.start_service_pending_intent)
        startButtonPendingIntent.setOnClickListener {
            startService<PendingIntentService> { intent ->
                intent.putExtra(
                    PENDING_INTENT_EXTRA_NAME,
                    createPendingResult(TASK_REQUEST_CODE, Intent(), 0)
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TASK_REQUEST_CODE -> {
                when (resultCode) {
                    START_SERVICE_RESULT_CODE,
                    UPDATE_SERVICE_RESULT_CODE,
                    FINISH_SERVICE_RESULT_CODE -> {
                        serviceResultOutputTextView.text =
                            data?.getStringExtra(SERVICE_EXTRA_NAME)
                    }
                }
            }
        }
    }

    /**
     * Использование BroadcastReceiver для обартной связи от сервиса
     */
    private fun initBroadcastReceiverServicePart() {
        serviceResultOutputBrTextView = findViewById(R.id.service_result_br_output_text_view)
        broadcastReceiver = ServiceResponseBroadcastReceiver(serviceResultOutputBrTextView)
        registerReceiver(
            broadcastReceiver,
            IntentFilter(ServiceResponseBroadcastReceiver.BROADCAST_ACTION)
        )
        startButtonBr = findViewById(R.id.start_service_broadcast_receiver)
        startButtonBr.setOnClickListener {
            startService<BroadcastReceiverService>()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}