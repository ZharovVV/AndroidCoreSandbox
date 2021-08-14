package com.github.zharovvv.android.core.sandbox.viewmodel.rxjava

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * # ViewModel
 * [ViewModel] - класс, позволяющий Activity и фрагментам сохранять необходимые им объекты живыми
 * при повороте экрана.
 * В метод ViewModelProviders.of передаем Activity (вызов в активити). Тем самым мы получим доступ
 * к провайдеру, который хранит все ViewModel для этого Activity.
 * Методом get запрашиваем у этого провайдера конкретную модель по имени класса - MyViewModel.
 * Если провайдер еще не создавал такой объект ранее, то он его создает и возвращает нам.
 * И пока Activity окончательно не будет закрыто, при всех последующих вызовах метода get
 * мы будем получать этот же самый объект MyViewModel.
 * Соответственно, при поворотах экрана, Activity будет пересоздаваться,
 * а объект MyViewModel будет спокойно себе жить в провайдере.
 * И Activity после пересоздания сможет получить этот объект обратно и продолжить работу,
 * как будто ничего не произошло.
 * Отсюда следует важный вывод. Не храните в ViewModel ссылки на Activity, фрагменты, View и пр.
 * Это может привести к утечкам памяти.
 * #
 * ## ViewModel как паттерн (MVVM)
 * ViewModel – класс, который соединяет View и Model.
 * ViewModel подписана на обновления Model, а View подписана на обновления ViewModel.
 * При этом ViewModel не имеет явной сслыки на View. Подписки реализуются через паттерн Observer.
 */
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