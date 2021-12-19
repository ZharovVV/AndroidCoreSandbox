package com.github.zharovvv.android.core.sandbox.di.example.providers

import com.github.zharovvv.android.core.sandbox.di.AppScope
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun provide(): Scheduler
}

//@Singleton
@AppScope
class IoSchedulerProvider
@Inject constructor() : SchedulerProvider {
    override fun provide(): Scheduler {
        return Schedulers.io()
    }
}

//@Singleton
@AppScope
class AndroidMainThreadSchedulerProvider
@Inject constructor() : SchedulerProvider {
    override fun provide(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}