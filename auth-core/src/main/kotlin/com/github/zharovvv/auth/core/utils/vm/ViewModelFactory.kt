package com.github.zharovvv.auth.core.utils.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(
    private val onCreateHook: ((T) -> Unit)? = null,
    private val create: () -> T
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = create()
        onCreateHook?.invoke(vm)
        return vm as T
    }
}