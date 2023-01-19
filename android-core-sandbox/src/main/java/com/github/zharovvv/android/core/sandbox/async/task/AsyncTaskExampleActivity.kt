package com.github.zharovvv.android.core.sandbox.async.task

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class AsyncTaskExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_async_task_example) {

    private lateinit var textView: TextView
    private lateinit var startButton: Button
    private lateinit var resultButton: Button

    private lateinit var asyncTask: CustomAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.activity_async_task_example_text_view)
        startButton = findViewById(R.id.activity_async_task_example_button)
        resultButton = findViewById(R.id.activity_async_task_example_button_result)

        val nullableAsyncTask = lastCustomNonConfigurationInstance as CustomAsyncTask?
        if (nullableAsyncTask != null) {
            asyncTask = nullableAsyncTask
            asyncTask.textView = textView
        } else {
            asyncTask = CustomAsyncTask(textView)
        }

        startButton.setOnClickListener {
            try {
                asyncTask.execute(// Если вызвать повторно будет брошено исключение
                        "1 сообщение",
                        "2 сообщение",
                        "3 сообщение",
                        "4 сообщение",
                        "5 сообщение"
                )
            } catch (e: IllegalStateException) {
                textView.text = e.message
            }
        }
        resultButton.setOnClickListener {
            try {
                textView.text = asyncTask.get() //Блокирует поток до тех пор пока не получит результат
            } catch (e: Exception) {
                textView.text = e.message
            }
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return asyncTask
    }

    override fun onDestroy() {
        super.onDestroy()
        asyncTask.textView = null
    }
}