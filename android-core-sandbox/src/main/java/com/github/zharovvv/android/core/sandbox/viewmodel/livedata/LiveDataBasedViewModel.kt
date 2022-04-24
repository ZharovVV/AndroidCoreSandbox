package com.github.zharovvv.android.core.sandbox.viewmodel.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class LiveDataBasedViewModel : ViewModel() {

    /**
     * # LiveData
     * LiveData - хранилище данных, работающее по принципу паттерна Observer (наблюдатель).
     * Это хранилище умеет делать две вещи:
     * 1) В него можно поместить какой-либо объект
     * 2) На него можно подписаться и получать объекты, которые в него помещают.
     *
     * Т.е. с одной стороны кто-то помещает объект в хранилище, а с другой стороны кто-то подписывается и получает этот объект.
     * #
     * * Если Activity было не активно во время обновления данных в LiveData,
     * то при возврате в активное состояние, его observer получит последнее актуальное значение данных.
     * * В момент подписки, observer получит последнее актуальное значение из LiveData.
     * * Если Activity будет закрыто, т.е. перейдет в статус DESTROYED, то LiveData автоматически отпишет от себя его observer.
     * * Если Activity в состоянии DESTROYED попробует подписаться, то подписка не будет выполнена.
     * * Если Activity уже подписывало свой observer, и попробует сделать это еще раз, то просто ничего не произойдет.
     * * Вы всегда можете получить последнее значение LiveData с помощью его метода getValue.
     * # Отправка данных в LiveData
     * Чтобы поместить значение в MutableLiveData, используется метод [MutableLiveData.setValue]
     * Этот метод обновит значение LiveData, и все его активные подписчики получат это обновление.
     * Метод [MutableLiveData.setValue] должен быть вызван из UI потока.
     * Для обновления данных из других потоков используйте метод [MutableLiveData.postValue].
     * Он перенаправит вызов в UI поток. Соответственно, подписчики всегда будут получать значения в основном потоке.
     */
    private val mutableLiveData: MutableLiveData<String> = MutableLiveData()
    val data: LiveData<String> = mutableLiveData

    private val compositeDisposable = CompositeDisposable()

    fun start() {
        Observable.create<String> { emitter ->
            for (i in 1..30) {
                emitter.onNext(i.toString())
            }
        }
            .doOnNext { Thread.sleep(1000L) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data: String -> mutableLiveData.value = data }
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