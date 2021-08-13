package com.github.zharovvv.android.core.sandbox.viewmodel.rxjava

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RxJavaBasedViewModel : ViewModel() {

    //hot observable
    private val behaviorSubject: BehaviorSubject<String> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()

    val data: Observable<String> = behaviorSubject

    fun start() {
        Observable.create<String> { emitter ->
            for (i in 1..30) {
                emitter.onNext(i.toString())
            }
        }
            .doOnNext { Thread.sleep(1000L) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data -> behaviorSubject.onNext(data) }
            .keep()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun Disposable.keep() {
        compositeDisposable += this
    }
}