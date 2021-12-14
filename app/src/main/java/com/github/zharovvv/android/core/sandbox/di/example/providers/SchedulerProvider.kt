package com.github.zharovvv.android.core.sandbox.di.example.providers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun provide(): Scheduler
}

class IoSchedulerProvider
@Inject constructor() : SchedulerProvider {
    override fun provide(): Scheduler {
        return Schedulers.io()
    }
}

class AndroidMainThreadSchedulerProvider
@Inject constructor() : SchedulerProvider {
    override fun provide(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}