package com.github.zharovvv.compose.sandbox.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.Closeable

class Compose1ViewModel : SimpleViewModel()

class Compose2ViewModel : SimpleViewModel()

class Compose3ViewModel : SimpleViewModel()

class Compose4ViewModel : SimpleViewModel()

abstract class SimpleViewModel : ViewModel() {
    init {
        Log.i("ComposeMainActivity-DEBUG", "$this#init")
        addCloseable {
            Log.i(
                "ComposeMainActivity-DEBUG",
                "$this#onClear"
            )
        }
    }

    final override fun addCloseable(closeable: Closeable) {
        super.addCloseable(closeable)
    }
}