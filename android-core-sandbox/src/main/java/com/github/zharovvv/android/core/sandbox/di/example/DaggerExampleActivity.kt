package com.github.zharovvv.android.core.sandbox.di.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.zharovvv.android.core.sandbox.databinding.ActivityDaggerExampleBinding
import com.github.zharovvv.android.core.sandbox.di.FeatureComponent
import com.github.zharovvv.android.core.sandbox.di.FeatureScope
import com.github.zharovvv.android.core.sandbox.di.example.analytics.Analytics
import com.github.zharovvv.android.core.sandbox.di.example.analytics.AnalyticsTracker
import com.github.zharovvv.android.core.sandbox.noncore.di.api.AndroidCoreSandboxApi
import com.github.zharovvv.common.di.featureApi
import javax.inject.Inject

class DaggerExampleActivity : AppCompatActivity() {

    //Второй способ более предпочтительный
    //@Inject
    //internal lateinit var computer: Computer

    /**
     *  Отложенная инициализация при помощи Lazy и Provider
     * Dagger does not support injecting Lazy<T>, Producer<T>, or Produced<T> when T
     * is an @AssistedFactory-annotated type such as com.github.zharovvv.android.core.sandbox.di.example.DaggerExampleViewModel.Factory.DiFactory
     * Lazy из Dagger. Отложенно инициализлурует зависимость.
     * (То есть объект создастся не при вызове appComponent.inject(this),
     * а при вызове Lazy<Object>.get()).
     * Особенность Lazy в том, что он резолвит компоненты и сохраняет их внутри.
     * Также есть Provider, который позволяет каждый раз при обращении к нему получать зависимость из графа.
     * Полезно например если мы хотим получать новый инстанс каждый раз (если зависимость не Singleton).
     */
    @Inject
    internal lateinit var viewModelDiFactory: DaggerExampleViewModel.Factory.DiFactory

    @Inject
    @FeatureScope
    internal lateinit var analytics: Analytics

    private var _featureComponent: FeatureComponent? = null
    private val featureComponent: FeatureComponent get() = _featureComponent!!

    private lateinit var lateInitId: String
    private val daggerExampleViewModel: DaggerExampleViewModel by viewModels {
        viewModelDiFactory.create(initId = lateInitId)
    }

    private lateinit var binding: ActivityDaggerExampleBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaggerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _featureComponent = featureApi<AndroidCoreSandboxApi>().legacy.featureComponent().build()
        featureComponent.inject(this)
        //Если бы не метод displayComputer, которому необходим доступ к view activity,
        //то мы могли бы заинжектить все зависимости в onAttach коллбеке.
//        appComponent.inject(this) //закоментили чтобы вызвать featureComponent.inject(this)
//        val computer: Computer = appComponent.computer() //первый способ
        lateInitId = "LateInitId: 100500"
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun requestData() {
                daggerExampleViewModel.requestData()
            }
        })
        daggerExampleViewModel.data.observe(this) {
            with(binding) {
                daggerExampleTextView.text = "Output: $it"
            }
        }
        analytics.sendEvent(
            event = AnalyticsTracker.Event(
                name = "testEvent",
                value = "TestValue"
            )
        )
    }

    //Третий способ: внедрение через метод
    /**
     * При инжекте в класс каких-то зависимостей будут также вызываться все методы, помеченные
     * аннотацией @Inject. При этом все параметры этих методов будут резолвиться из графа зависимостей
     * и доставляться в метод.
     */
    @SuppressLint("SetTextI18n")
    @Inject
    internal fun displayComputerAndFeature(
        computer: Computer,
        dependencyExample: DependencyExample,
        featureExample: FeatureExample
    ) {
        with(binding) {
            daggerExampleTextView.text =
                "computer: $computer\n" +
                        "dependencyExample: ${dependencyExample.data}\n" +
                        "featureExample: ${featureExample.data}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _featureComponent = null
    }
}