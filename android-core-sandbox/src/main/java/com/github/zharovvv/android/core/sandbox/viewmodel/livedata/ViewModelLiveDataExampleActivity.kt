package com.github.zharovvv.android.core.sandbox.viewmodel.livedata

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class ViewModelLiveDataExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_view_model_livedata_example) {

    companion object {
        private const val LOG_TAG = "LifecycleComponent"
    }

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
        liveDataBasedViewModel.data.observe(this, Observer { data: String ->
            textView.text = data
        })

        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
            fun someFun() {
                Log.i(LOG_TAG, "LifecycleObserver#someFun")
            }
        })
        // При добавлении LifecycleEventObserver-а к списку наблюдающих — наблюдатель получит события
        // об изменении всех состояний, которые предшествуют текущему.
        // То есть если LifecycleOwner находится в состоянии Lifecycle.State.STARTED
        // при добавлении LifecycleObserver,
        // то последний получит два события Lifecycle.Event.ON_CREATE и Lifecycle.Event.ON_START.
        //
        // Значит, мы имеем гарантию того,
        // что наш наблюдатель пройдет все ступени инициализации,
        // опираясь на события жизненного цикла, и не пропустит какой-либо стадии конфигурации.
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                val sourceCurrentState: Lifecycle.State = source.lifecycle.currentState
                Log.i(
                    LOG_TAG,
                    "LifecycleEventObserver#onStateChanged; sourceCurrentState: $sourceCurrentState; event: $event;"
                )
            }
        })
    }
}