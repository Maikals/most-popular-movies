package com.example.domain.base

import com.example.domain.entity.BaseEntity
import com.example.domain.exceptions.CustomException
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

abstract class BaseCoRoutineUseCase<T : BaseEntity, Params> {

    var job: Job? = null

    fun execute(params: Params, block: suspend CoroutineScope.(T) -> Unit,
                blockError: (CustomException?) -> Unit) {
        launchAsync {
            try {
                asyncAwait {
                    block(buildRepoCall(params))
                }
            } catch (e: CustomException) {
                println("BaseCoRoutineUseCase: $e")
                blockError(e)
            }
        }
    }


    abstract fun buildRepoCall(params: Params): T

    fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        job = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, {
        }, {
            block()
        })
    }

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, {
        }, { block() })
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