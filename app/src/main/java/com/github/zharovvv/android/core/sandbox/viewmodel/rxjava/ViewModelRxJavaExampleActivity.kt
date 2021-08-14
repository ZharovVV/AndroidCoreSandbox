package com.github.zharovvv.android.core.sandbox.viewmodel.rxjava

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class ViewModelRxJavaExampleActivity :
    LogLifecycleAppCompatActivity(R.layout.activity_view_model_rxjava_example) {

    private lateinit var startButton: Button
    private lateinit var textView: TextView

    //lazy call ViewModelProvider(store, factory).get(viewModelClass.java)
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