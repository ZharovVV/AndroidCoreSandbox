package com.github.zharovvv.compose.sandbox.ext.compose

import androidx.compose.runtime.MutableState

//TODO переделать на Flow (тогда и класс не нужен будет)
//Костыль!!
private class MutableStateDistinctUntilChanged<T>(
    private val source: MutableState<T>
) : MutableState<T> by source {

    override var value: T
        get() = source.value
        set(value) {
            if (value != source.value) {
                source.value = value
            }
        }

}

fun <T> MutableState<T>.distinctUntilChanged(): MutableState<T> =
    MutableStateDistinctUntilChanged(source = this)