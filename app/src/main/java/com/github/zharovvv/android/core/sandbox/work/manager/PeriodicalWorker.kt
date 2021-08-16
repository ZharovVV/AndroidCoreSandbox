package com.github.zharovvv.android.core.sandbox.work.manager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicalWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val UNIQUE_PERIODICAL_WORK_NAME = "unique_periodical_work_name"
    }

    override fun doWork(): Result {
        makeNotification("periodical work", applicationContext)
        return Result.success()
    }
}