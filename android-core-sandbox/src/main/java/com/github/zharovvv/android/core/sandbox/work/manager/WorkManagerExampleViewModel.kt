package com.github.zharovvv.android.core.sandbox.work.manager

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.*
import com.github.zharovvv.android.core.sandbox.work.manager.MyWorker.Companion.UNIQUE_WORK_TAG
import com.github.zharovvv.android.core.sandbox.work.manager.PeriodicalWorker.Companion.UNIQUE_PERIODICAL_WORK_NAME
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerExampleViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    private var isWorkStarted = false
    private val mediatorLiveData: MediatorLiveData<WorkInfo> = MediatorLiveData()
    val workInfoData: LiveData<WorkInfo> = mediatorLiveData

    init {
        findNonFinishedWorkId()?.let { workId ->
            isWorkStarted = true
            mediatorLiveData.addSource(workManager.getWorkInfoByIdLiveData(workId)) {
                if (it.state.isFinished) {
                    isWorkStarted = false
                }
                mediatorLiveData.value = it
            }
        }
    }

    fun start() {
        if (!isWorkStarted) {
            val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
//                .setInitialDelay(Duration.ofHours(2)) //>= API 26 (O) Указывает WM запускать работу по прошествии 2 часов
//с момента постановки в очередь
//                .setConstraints(
//                    Constraints.Builder().setRequiresCharging(true).build()
//                )//Ограничение: WM запустит работу только когда телефон заряжается
                .addTag(UNIQUE_WORK_TAG)
                .build()

            mediatorLiveData.addSource(workManager.getWorkInfoByIdLiveData(workRequest.id)) { workInfo ->
                if (workInfo.state.isFinished) {
                    isWorkStarted = false
                }
                mediatorLiveData.value = workInfo
            }
            isWorkStarted = true
            workManager.enqueue(workRequest)
        }
    }

    private fun findNonFinishedWorkId(): UUID? {
        return workManager.getWorkInfosByTag(UNIQUE_WORK_TAG)
            .also { Log.i(LOG_WORK_TAG, "start Future.get") }
            .get()  //Операция занимает 5 мс
            .also { Log.i(LOG_WORK_TAG, "end Future.get") }
            ?.also { Log.i(LOG_WORK_TAG, "$it") }
            ?.firstOrNull { workInfo: WorkInfo -> !workInfo.state.isFinished }
            ?.id
    }

    /**
     * ```
     * [     before flex     |     flex     ][     before flex     |     flex     ]...
     * [   cannot run work   | can run work ][   cannot run work   | can run work ]...
     * \____________________________________/\____________________________________/...
     *                interval 1                            interval 2             ...(repeat)
     * ```
     * Обращу внимание, что после переустановки приложения запланированная работа продолжилась.
     */
    fun schedulePeriodicWork() {
        val uniqueWorkName = UNIQUE_PERIODICAL_WORK_NAME
        val existingWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicalWorker>(
            repeatInterval = 15L,   //должен быть >= 15 мин
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
            flexTimeInterval = 5L,  //должен быть >= 5 мин
            flexTimeIntervalUnit = TimeUnit.MINUTES
        ).build()
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            existingWorkPolicy,
            periodicWorkRequest
        )
    }

    fun cancelPeriodicWork() {
        workManager.cancelUniqueWork(UNIQUE_PERIODICAL_WORK_NAME)
    }

    override fun onCleared() {
        super.onCleared()
//        workManager.cancelAllWorkByTag(UNIQUE_WORK_TAG)
    }
}