package com.github.zharovvv.android.core.sandbox.viewmodel.livedata

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class ViewModelLiveDataExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_view_model_livedata_example) {

    private lateinit var startButton: Button
    private lateinit var textView: TextView

    private val liveDataBasedViewModel: LiveDataBasedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.button_start)
        textView = findViewById(R.id.text_view_simple)

        startButton.setOnClickListener {
            liveDataBasedViewModel.start()
        }

        // В метод observe нам необходимо передать два параметра:
        //Первый - это LifecycleOwner.
        //LifecycleOwner - это интерфейс с методом getLifecycle.
        // Activity и фрагменты в Support Library, начиная с версии 26.1.0 реализуют этот интерфейс,
        // поэтому мы передаем this.
        //LiveData получит из Activity его Lifecycle и по нему будет определять состояние Activity.
        //Активным считается состояние STARTED или RESUMED. Т.е. если Activity видно на экране,
        //то LiveData считает его активным и будет отправлять данные в его колбэк.
        //
        // Второй параметр - это непосредственно подписчик, т.е. колбэк,
        // в который LiveData будет отправлять данные.
        // В нем только один метод onChanged. В нашем примере туда будет приходить String.
        liveDataBasedViewModel.data.observe(this) { data: String ->
            textView.text = data
        }
    }
}