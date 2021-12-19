package com.github.zharovvv.android.core.sandbox.di.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.zharovvv.android.core.sandbox.appComponent
import com.github.zharovvv.android.core.sandbox.databinding.ActivityDaggerExampleBinding
import javax.inject.Inject

class DaggerExampleActivity : AppCompatActivity() {

    //Второй способ более предпочтительный
    //@Inject
    //internal lateinit var computer: Computer

    @Inject
    internal lateinit var viewModelDiFactory: DaggerExampleViewModel.Factory.DiFactory
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
        //Если бы не метод displayComputer, которому необходим доступ к view activity,
        //то мы могли бы заинжектить все зависимости в onAttach коллбеке.
        appComponent.inject(this)
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
    }

    //Третий способ: внедрение через метод
    /**
     * При инжекте в класс каких-то зависимостей будут также вызываться все методы, помеченные
     * аннотацией @Inject. При этом все параметры этих методов будут резолвиться из графа зависимостей
     * и доставляться в метод.
     */
    @Inject
    internal fun displayComputer(computer: Computer) {
        with(binding) {
            daggerExampleTextView.text = computer.toString()
        }
    }
}