package com.github.zharovvv.android.core.sandbox.fragment

import android.util.Log
import androidx.lifecycle.ViewModel

class ExampleViewModel : ViewModel() {

    init {
        Log.i(TAG, "$this#init")
    }

    override fun onCleared() {
        Log.i(TAG, "$this#onCleared")
    }

    private companion object {
        const val TAG = "ExampleViewModel"
    }
}