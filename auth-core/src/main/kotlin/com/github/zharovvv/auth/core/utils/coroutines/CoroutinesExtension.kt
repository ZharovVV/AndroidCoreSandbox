package com.github.zharovvv.auth.core.utils.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


suspend inline fun <T> cancellableRunCatching(action: () -> T): Result<T> =
    try {
        Result.success(action())
    } catch (cancellableException: CancellationException) {
        if (currentCoroutineContext()[Job]?.isActive == true) {
            Result.failure(cancellableException)
        } else {
            throw cancellableException
        }
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }

fun <T> Flow<T>.launchInWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) = lifecycle.coroutineScope.launch {
    lifecycle.repeatOnLifecycle(minActiveState) {
        collect()
    }
}