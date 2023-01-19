package com.github.zharovvv.android.core.sandbox.service

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.SERVICE_INPUT
import com.github.zharovvv.android.core.sandbox.service.ExampleService.Companion.START_SERVICE_CONSTANT
import com.github.zharovvv.android.core.sandbox.service.PendingIntentService.Companion.SERVICE_EXTRA_NAME
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

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

    private lateinit var serviceResultOutputBindingTextView: TextView
    private lateinit var startBindingServiceButton: Button
    private lateinit var bindServiceButton: Button
    private lateinit var unbindServiceButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSimpleServicePart()
        initPendingIntentServicePart()
        initBroadcastReceiverServicePart()
        initBindingServicePart()
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

    private lateinit var mService: BindingService.LocalBinder
    private var isServiceBinding: Boolean = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isServiceBinding = true
            Log.i("ServiceLifecycle", "ServiceConnection#onServiceConnected")
            //Если сервис находится в другом процессе то service это экземпляр класса android.os.BinderProxy
            service as BindingService.LocalBinder
            mService = service
            mService.addUpdateListener { data ->
                serviceResultOutputBindingTextView.text = data
            }
        }

        //Срабатывает когда уничтожается процесс, в котором был запущен сервис.
        //Метод onServiceDisconnected не сработает при явном unbind-е.
        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBinding = false
            Log.i("ServiceLifecycle", "ServiceConnection#onServiceDisconnected")
        }
    }

    /**
     * Использование Binding для обратной связи от сервиса
     */
    private fun initBindingServicePart() {
        val bindingServiceIntent = Intent(this, BindingService::class.java)
        serviceResultOutputBindingTextView =
            findViewById(R.id.service_result_binding_output_text_view)
        startBindingServiceButton = findViewById(R.id.start_service_binding)
        startBindingServiceButton.setOnClickListener {
            startService(bindingServiceIntent)
        }
        bindServiceButton = findViewById(R.id.bind_service_button)
        bindServiceButton.setOnClickListener {
            bindService(
                bindingServiceIntent,
                serviceConnection,
                BIND_AUTO_CREATE    //если сервис, к которому мы пытаемся подключиться, не создан, то он будет создан.
            )
        }
        unbindServiceButton = findViewById(R.id.unbind_service_button)
        unbindServiceButton.setOnClickListener {
            if (isServiceBinding) {
                Log.i("ServiceLifecycle", "unbind Service (call from Activity)")
                unbindService(serviceConnection)
                mService.removeListener()
                serviceResultOutputBindingTextView.text = getString(R.string.unbind_service_text)
                isServiceBinding = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        if (isServiceBinding) { //Если сервис не будет зарегистрирован и вызвать этот метод
            //java.lang.IllegalArgumentException: Service not registered
            unbindService(serviceConnection)
            mService.removeListener()
            isServiceBinding = false
        }
    }
}