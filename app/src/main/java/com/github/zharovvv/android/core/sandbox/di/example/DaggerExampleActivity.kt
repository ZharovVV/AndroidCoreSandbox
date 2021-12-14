package com.github.zharovvv.android.core.sandbox.di.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.appComponent
import com.github.zharovvv.android.core.sandbox.databinding.ActivityDaggerExampleBinding
import javax.inject.Inject

class DaggerExampleActivity : AppCompatActivity() {

    //Второй способ более предпочтительный
    @Inject
    internal lateinit var computer: Computer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        val binding = ActivityDaggerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val computer: Computer = appComponent.computer() //первый способ
        with(binding) {
            daggerExampleTextView.text = computer.toString()
        }
    }
}