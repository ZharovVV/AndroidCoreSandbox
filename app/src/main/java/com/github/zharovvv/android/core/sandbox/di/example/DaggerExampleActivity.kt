package com.github.zharovvv.android.core.sandbox.di.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.appComponent
import com.github.zharovvv.android.core.sandbox.databinding.ActivityDaggerExampleBinding
import com.github.zharovvv.android.core.sandbox.di.example.providers.SchedulerProvider
import com.github.zharovvv.android.core.sandbox.di.example.repository.ExampleRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject
import javax.inject.Named

class DaggerExampleActivity : AppCompatActivity() {

    //    //Второй способ более предпочтительный
//    @Inject
//    internal lateinit var computer: Computer
    @Inject
    internal lateinit var exampleRepository: ExampleRepository

    @Inject
    @Named("mainThread")
    internal lateinit var schedulerProvider: SchedulerProvider

    private lateinit var binding: ActivityDaggerExampleBinding
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaggerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appComponent.inject(this)
//        val computer: Computer = appComponent.computer() //первый способ
        compositeDisposable += exampleRepository.getData()
            .observeOn(schedulerProvider.provide())
            .subscribe {
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}