package com.github.zharovvv.compose.sandbox.models.ui

sealed class ViewState<T> {

    class Success<T>(val result: T) : ViewState<T>()

    class Error<T>(val error: Throwable) : ViewState<T>()

    class Loading<T> : ViewState<T>()
}
