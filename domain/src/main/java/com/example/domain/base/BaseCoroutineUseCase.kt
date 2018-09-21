package com.example.domain.base

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

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
        job = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, {

        }, { block() })
    }

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, null, { block() })
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }

    fun cancel() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            job?.cancelAndJoin()
        })
    }
}