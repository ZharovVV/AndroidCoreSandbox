package com.github.zharovvv.android.core.sandbox.work.manager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi

/**
 * # WorkManager
 * WorkManager - новый инструмент.
 * Он позволяет запускать фоновые задачи последовательно или параллельно, передавать в них данные,
 * получать из них результат, отслеживать статус выполнения и запускать только при соблюдении заданных условий.
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
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
        val startActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(applicationContext, WorkManagerExampleActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            PendingIntent.FLAG_IMMUTABLE
        )
        //non UI Thread
        val notificationUtil = featureApi<AndroidCoreSandboxApi>().notificationUtil
        Log.i(LOG_WORK_TAG, "MyWorker#doWork/start")
        notificationUtil.sendNotification(
            title = "WorkManager",
            text = "work started...",
            contentIntent = startActivityPendingIntent
        )
        for (i in 1..30) {
            Thread.sleep(1000L)
            setProgressAsync(
                Data.Builder()
                    .putString(WORK_OUTPUT_DATA_STRING, i.toString())
                    .build()
            )
        }
        Log.i(LOG_WORK_TAG, "MyWorker#doWork/finish")
        notificationUtil.sendNotification(
            title = "WorkManager",
            text = "The work is done!",
            contentIntent = startActivityPendingIntent,
            autoCancel = true
        )
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