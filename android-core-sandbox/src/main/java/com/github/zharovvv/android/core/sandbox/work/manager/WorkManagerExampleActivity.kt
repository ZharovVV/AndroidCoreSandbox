package com.github.zharovvv.android.core.sandbox.work.manager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.work.manager.MyWorker.Companion.WORK_OUTPUT_DATA_STRING
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class WorkManagerExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_work_manager_example) {

    private lateinit var startButton: Button
    private lateinit var textView: TextView

    private lateinit var startPeriodicWorkButton: Button
    private lateinit var cancelPeriodicWorkButton: Button

    private val viewModel: WorkManagerExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        startButton.setOnClickListener {
            viewModel.start()
        }
        subscribeWorkInfoLiveData()
        startPeriodicWorkButton.setOnClickListener {
            viewModel.schedulePeriodicWork()
        }
        cancelPeriodicWorkButton.setOnClickListener {
            viewModel.cancelPeriodicWork()
        }
    }

    private fun bindViews() {
        startButton = findViewById(R.id.button_start)
        textView = findViewById(R.id.text_view_simple)
        startPeriodicWorkButton = findViewById(R.id.button_start_periodic_work)
        cancelPeriodicWorkButton = findViewById(R.id.button_cancel_periodic_work)
    }

    private fun subscribeWorkInfoLiveData() {
        viewModel.workInfoData.observe(this) { workInfo ->
            Log.i(LOG_WORK_TAG, "$workInfo")
            if (workInfo.state.isFinished) {
                textView.text = workInfo.outputData.getString(WORK_OUTPUT_DATA_STRING)
            } else {
                textView.text = workInfo.progress.getString(WORK_OUTPUT_DATA_STRING)
            }
        }
    }
}