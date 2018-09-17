package com.example.domain.base

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

abstract class BaseCoroutineUseCase<T, Params> {

    var job: Job? = null

    fun execute(params: Params, block: suspend CoroutineScope.(T) -> Unit){
        cancel()
        launchAsync {
            asyncAwait {
                block(buildRepoCall(params))
            }
        }
    }

    abstract fun buildRepoCall(params: Params) : T

    fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        job = launch(UI) { block() }
    }

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(CommonPool) { block() }
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }

    fun cancel() {
        launch {
            job?.cancelAndJoin()
        }
    }
}