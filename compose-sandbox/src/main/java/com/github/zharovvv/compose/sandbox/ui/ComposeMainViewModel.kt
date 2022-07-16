package com.github.zharovvv.compose.sandbox.ui

import androidx.lifecycle.ViewModel
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.di.api.internal.ui.ViewModelScope
import com.github.zharovvv.compose.sandbox.models.ui.CardItem
import com.github.zharovvv.compose.sandbox.models.ui.ViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Closeable

class ComposeMainViewModel(
    private val viewModelScope: ViewModelScope
) : ViewModel(viewModelScope, Closeable { releaseFeature<ComposeSandboxApi>() }),
    CoroutineScope by viewModelScope {

    private val _cardsStateFlow: MutableStateFlow<ViewState<List<CardItem>>> =
        MutableStateFlow(ViewState.Loading())
    val cardsStateFlow: StateFlow<ViewState<List<CardItem>>> get() = _cardsStateFlow

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        launch {
            _cardsStateFlow.emit(ViewState.Error(throwable))
        }
    }

    fun requestList() {
        launch(errorHandler) {
            val cards = emulateLongOperation()
            _cardsStateFlow.emit(ViewState.Success(cards))
        }
    }

    private suspend fun emulateLongOperation(): List<CardItem> = withContext(Dispatchers.IO) {
        delay(3000)
        listOf("first", "second", "third", "fourth").map { CardItem(text = it) }
    }

}