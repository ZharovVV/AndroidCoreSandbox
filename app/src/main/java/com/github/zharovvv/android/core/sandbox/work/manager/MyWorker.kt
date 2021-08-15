package com.github.zharovvv.android.core.sandbox.work.manager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * # WorkManager
 * WorkManager - новый инструмент.
 * Он позволяет запускать фоновые задачи последовательно или параллельно, передавать в них данные,
 * получать из них результат, отслеживать статус выполнения и запускать только при соблюдении заданных условий.
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val LOG_WORK_TAG = "log_work_tag"

        const val UNIQUE_WORK_TAG = "my_worker_work_tag"
        const val WORK_OUTPUT_DATA_STRING = "work_output_data_string"
    }

    /**
     * При помещении задачи в очередь её статус становится [WorkInfo.State.ENQUEUED]
     * Затем WorkManager определяет, что задачу можно запускать и вызывает метод [doWork].
     * Статус задачи при этом становится - [WorkInfo.State.RUNNING].
     * Затем статус задачи меняется в зависимости от того, что вернул метод [doWork].
     */
    override fun doWork(): Result {
        //non UI Thread
        Log.i(LOG_WORK_TAG, "MyWorker#doWork/start")
        makeNotification("work started...", applicationContext)
        for (i in 1..30) {
            Thread.sleep(1000L)
            setProgressAsync(
                Data.Builder()
                    .putString(WORK_OUTPUT_DATA_STRING, i.toString())
                    .build()
            )
        }
        Log.i(LOG_WORK_TAG, "MyWorker#doWork/finish")
        makeNotificationAutoDismiss("The work is done!", applicationContext)
        return Result.success(
            Data.Builder()
                .putString(WORK_OUTPUT_DATA_STRING, "Work is Done!")
                .build()
        )
    }

    override fun onStopped() {
        Log.i(LOG_WORK_TAG, "MyWorker#onStopped")
        super.onStopped()
    }
}