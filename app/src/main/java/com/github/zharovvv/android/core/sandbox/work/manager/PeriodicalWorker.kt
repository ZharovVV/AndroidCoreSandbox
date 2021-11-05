package com.github.zharovvv.android.core.sandbox.work.manager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.zharovvv.android.core.sandbox.AndroidCoreSandboxApplication

class PeriodicalWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val UNIQUE_PERIODICAL_WORK_NAME = "unique_periodical_work_name"
    }

    override fun doWork(): Result {
        val notificationUtil = AndroidCoreSandboxApplication.notificationUtil
        val startActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(applicationContext, WorkManagerExampleActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            0
        )
        notificationUtil.sendNotification(
            title = "WorkManager",
            text = "periodical work",
            contentIntent = startActivityPendingIntent
        )
        return Result.success()
    }
}