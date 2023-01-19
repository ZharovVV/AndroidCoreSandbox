package com.github.zharovvv.android.core.sandbox.viewmodel.rxjava

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class ViewModelRxJavaExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_view_model_rxjava_example) {

    private lateinit var startButton: Button
    private lateinit var textView: TextView

    /**
     * # Создание ViewModel
     * * __1 Способ__:
     * ```
     * ViewModelProvider(
     *  viewModelStore, // ComponentActivity реализует интерфейс ViewModelStoreOwner с методом getViewModelStore
     *  object : ViewModelProvider.Factory {
     *      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     *          return RxJavaBasedViewModel() as T
     *      }
     *  }
     * ).get<RxJavaBasedViewModel>()
     * ```
     * Создаем ViewModelProvider, передавая в конструктор объекты ViewModelStore и ViewModelProvider.Factory.
     * ViewModelStore содержит HashMap<String, ViewModel> mMap.
     * При вызове метода get в первый раз фабрика создает ViewModel,
     * а затем сохраняет viewModel в ViewModelStore.mMap по ключу
     * "androidx.lifecycle.ViewModelProvider.DefaultKey:<veiwModelClass.canonicalName>"
     * и возвращает созданный объект viewModel.
     *
     * * __2 Способ__:
     * ```
     * ViewModelProvider(this).get(RxJavaBasedViewModel::class.java)
     * ```
     * в данном примере this это объект, реализующий интерфейс ViewModelStoreOwner.
     * Под капотом все также вызывается первый способ.
     * При таком способе создания стоит учитывать, что будет использоваться фабрика по умолчанию.
     * Если объект ViewModelStoreOwner, передаваемый в конструктор провайдера, реализует интерфейс
     * HasDefaultViewModelProviderFactory
     * (Например если в конструктор провайдера передать объекты типа ComponentActivity, Fragment),
     * то будет вызываться метод getDefaultViewModelProviderFactory
     * (который во всех реализациях возвращает SavedStateViewModelFactory). Но есть свои нюансы.
     * Например вызов метода ComponentActivity.getDefaultViewModelProviderFactory вызовет исключение
     * IllegalStateException("Your activity is not yet attached to the Application instance.
     * You can't request ViewModel before onCreate call."), если активити не приаттачена к Application.
     * Для остальных объектов (непонятно только для каких, ведь viewModel будет использоваться в основном
     * в Activity и Fragment) фабрикой по умолчанию будет NewInstanceFactory.
     *
     * * __3 Способ__ (самый модный):
     * При помощи делегата ViewModelLazy
     * ```
     * private val viewModel: RxJavaBasedViewModel by viewModels()
     * ```
     * Сигнатура метода следующая:
     * ```
     *  public inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
     *      noinline factoryProducer: (() -> Factory)? = null
     *  ): Lazy<VM>
     * ```
     * Под капотом используется все тот же 1-ый способ, но только создание viewModel будет "ленивым".
     *
     * # Где хранится ViewModel?
     * Как уже было сказано выше ViewModel сохраняется в HashMap ViewModelStore, который передают
     * в конструктор ViewModelProvider-а.
     * В данном примере мы передаем в конструктор ViewModelProvider-а ViewModelStore активити,
     * вызывая метод ComponentActivity.getViewModelStore. Откуда берется ViewModelStore у активити,
     * и что с ним происходит, когда активити уничтожается системой (при смене конфигурации, например)?
     * * ViewModelStore создается, а затем сетится в поле mViewModelStore ComponentActivity
     * при первом вызове метода getViewModelStore.
     * * При пересоздании активити вызывается метод onRetainNonConfigurationInstance TODO уточнить как работает и когда точно этот метод вызывается
     * (у активити, которая будет уничтожена). ComponentActivity реализует этот метод следующим образом:
     * создается объект NonConfigurationInstances. В него кладется mViewModelStore. Метод
     * onRetainNonConfigurationInstance возвращает созданный объект NonConfigurationInstances.
     * * После пересоздания новый экземпляр активити при запросе ComponentActivity.getViewModelStore вызывает
     * getLastNonConfigurationInstance, откуда достает NonConfigurationInstances, а из него уже и сам ViewModelStore.
     */
    //by viewModels() is same to lazy call ViewModelProvider(store, factory).get(viewModelClass.java)
    private val viewModel: RxJavaBasedViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startButton = findViewById(R.id.button_start)
        textView = findViewById(R.id.text_view_simple)

        //Input
        startButton.setOnClickListener {
            viewModel.start()
        }

        //Output
        viewModel.data.subscribe { textData ->
            textView.text = textData
        }.keep()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun Disposable.keep() {
        compositeDisposable += this
    }
}