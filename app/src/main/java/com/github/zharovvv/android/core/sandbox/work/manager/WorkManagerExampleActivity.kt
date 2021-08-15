package com.github.zharovvv.android.core.sandbox.work.manager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.work.manager.MyWorker.Companion.LOG_WORK_TAG
import com.github.zharovvv.android.core.sandbox.work.manager.MyWorker.Companion.WORK_OUTPUT_DATA_STRING

class WorkManagerExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_work_manager_example) {

    private lateinit var startButton: Button
    private lateinit var textView: TextView

    private val viewModel: WorkManagerExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startButton = findViewById(R.id.button_start)
        textView = findViewById(R.id.text_view_simple)

        startButton.setOnClickListener {
            viewModel.start()
        }

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