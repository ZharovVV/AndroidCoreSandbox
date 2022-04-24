package com.github.zharovvv.android.core.sandbox.work.manager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi

class PeriodicalWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val UNIQUE_PERIODICAL_WORK_NAME = "unique_periodical_work_name"
    }

    override fun doWork(): Result {
        Log.e(LOG_WORK_TAG, "PeriodicalWorker#doWork")
        val notificationUtil = featureApi<AndroidCoreSandboxApi>().notificationUtil
        val startActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(applicationContext, WorkManagerExampleActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            0
        )
        notificationUtil.sendNotification(
            notificationId = 2,
            title = "WorkManager",
            text = "periodical work",
            contentIntent = startActivityPendingIntent,
            autoCancel = true
        )
        return Result.success()
    }
}