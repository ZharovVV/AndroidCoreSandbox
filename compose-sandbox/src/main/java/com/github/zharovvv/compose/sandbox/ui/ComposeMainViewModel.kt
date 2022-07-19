package com.github.zharovvv.compose.sandbox.ui

import android.app.Activity
import android.app.Service
import androidx.lifecycle.*
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.models.ui.CardItem
import com.github.zharovvv.compose.sandbox.models.ui.ViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Closeable

/**
 * # Корутины в Android
 * ## Бибилиотека kotlinx-coroutines-android
 * Данная библиотека содержит специальные настройки и расширения для Андроид-разработки:
 * * Реализация [Dispatchers.Main] на основе MainThread
 * * Правила R8/Proguard
 * * Специальный [CoroutineExceptionHandler], адаптированный под Андроид OS
 *
 * #
 * ## Специальные [CoroutineScope]
 * В Андроид очень много приходится взаимодействовать с жизненным циклом.
 * Вы не неайдете корутин-скоупа, который привязан к ЖЦ стандартных компонентов, например
 * [Activity] или [Service], но в котлин-расширениях библиотек Jetpack для Lifecycle и ViewModel
 * добавили специальные корутин-скоупы, соответствующие их ЖЦ.
 * Самый популярный скоуп [ViewModel.viewModelScope] (стартует вместе с ViewModel и закрывается в
 * [ViewModel.onCleared].
 *
 * Не всегда может получиться так, что корутину нужно запустить именно из вью-модели.
 * Например - нужно собрать данные, которые нужно обновлять каждый раз при открытии экрана.
 * Для этого можно воспользоваться ktx-расширениями библиотеки lifecycle, которая позволяет
 * получить скоуп из [LifecycleOwner.lifecycleScope] и [Lifecycle.coroutineScope].
 * Также lifecycleScope позволяет запустить suspend-функцию или корутину в определенный момент ЖЦ.
 * Для этого есть специальные функции
 * * [LifecycleCoroutineScope.launchWhenCreated] (под капотом вызывается [Lifecycle.whenCreated])
 * * [LifecycleCoroutineScope.launchWhenStarted] (под капотом вызывается [Lifecycle.whenStarted])
 * * [LifecycleCoroutineScope.launchWhenResumed] (под капотом вызывается [Lifecycle.whenResumed])
 * Важной особенностью when-функции является то, что они останавливаются когда у соответствующего
 * им ЖЦ произойдет событие ON_DESTROY, хотя вы можете вызвать whenStarted и ожидать конец вызова
 * в onStop, но это произойдет именно в onDestroy, из-за особенностей реализации [LifecycleCoroutineScope].
 *
 * Также вы можете попасть в неприятную ситуацию при миграции с [LiveData] на [Flow].
 * Так как observe с ЖЦ у [LiveData] и [Flow] с запуском [Flow.collect] в
 * [LifecycleCoroutineScope].launchWhen... будут иметь разный момент отписки, что ведет к потенциальным багам.
 *
 * #
 * ## [Lifecycle.repeatOnLifecycle]
 *
 * ```kotlin
 *  lifecycle.coroutineScope.launch {
 *      repeatOnLifecycle(Lifecycle.State.STARTED) {
 *          // блок начнет выполняться после вызова onStart()
 *          // и остановится в onStop() (если он не завершится раньше, из-за ошибки например)
 *      }
 *      // код здесь выполнится после выполнения кода в repeatOnLifecycle (если блок кинет эксепшн)
 *      // либо после вызова onStop()
 *      ...
 *  }
 * ```
 *
 * Функция [Lifecycle.repeatOnLifecycle] позволяет запустить выполнение кода в заданный момент ЖЦ
 * и остановить его в соответствующий ему закрывающем событии цикла.
 *
 * Например, если вы запустите repeatOnLifecycle с событием STARTED, то функция не остановится
 * до тех пор пока не будет выполнена, либо не пройдет событие STOP. И то, только в этот момент запустится
 * процесс отмены выполнения, которое в зависимости от вашего кода внутри ещё возможно придется обрабатывать
 * самостоятельно.
 *
 * Так как [repeatOnLifecycle] - это suspend-функция, то её нужно вызывать в корутине и тут всплывает
 * важный аспект - **эта функция приостановит выполнение корутины** до тех пор пока не произойдет **финальное
 * событие в ЖЦ**. Поэтому рекомендация - __вызывать [repeatOnLifecycle] в отдельной корутине__
 * и больше другого кода туда не писать, если не хотим дождаться когда закончится выполнение
 * этого кода (например прописать, что сделать после onStop()).
 *
 * #
 * ## Корутины в [LiveData]
 * См. [liveData] (лень добавлять библиотеку в зависимости :()
 * Функция-мост между корутинами и [LiveData].
 *
 * #
 * ## [Flow]
 * См. [Flow.flowWithLifecycle] - под капотом вызывает [repeatOnLifecycle]
 */
class ComposeMainViewModel : ViewModel(Closeable { releaseFeature<ComposeSandboxApi>() }) {

    private val _cardsStateFlow: MutableStateFlow<ViewState<List<CardItem>>> =
        MutableStateFlow(ViewState.Loading())
    val cardsStateFlow: StateFlow<ViewState<List<CardItem>>> get() = _cardsStateFlow

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _cardsStateFlow.emit(ViewState.Error(throwable))
        }
    }

    fun requestList() {
        viewModelScope.launch(errorHandler) {
            val cards = emulateLongOperation()
            _cardsStateFlow.emit(ViewState.Success(cards))
        }
    }

    private suspend fun emulateLongOperation(): List<CardItem> = withContext(Dispatchers.IO) {
        delay(3000)
        listOf("first", "second", "third", "fourth").map { CardItem(text = it) }
    }

}