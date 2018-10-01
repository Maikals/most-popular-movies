package com.example.domain.base

import com.example.domain.entity.BaseEntity
import com.example.domain.exceptions.CustomException
import com.example.domain.exceptions.ExceptionType
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

abstract class BaseCoRoutineUseCase<T : BaseEntity, Params> {

    private var job: Job? = null

    fun execute(params: Params, block: suspend CoroutineScope.(T) -> Unit,
                blockError: (CustomException?) -> Unit) {
        job = launchAsync {
            async({
                block(buildRepoCall(params))
            }, {
                blockError(it)
            })
        }
    }

    abstract suspend fun buildRepoCall(params: Params): T

    fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job =
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, {
                if (it != null) println("BaseCoRoutineUseCase: $it")
            }, {
                block()
            })

    suspend fun <T> async(block: suspend CoroutineScope.() -> T, blockError: (CustomException?) -> T): Deferred<T> {
        return GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, {
            if (it != null) {
                println("BaseCoRoutineUseCase: $it")
                blockError(CustomException(it, ExceptionType.UNDEFINED))
            }
        }, {
            block()
        })
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T, blockError: (CustomException?) -> T): T {
        return async(block, blockError).await()
    }

    fun cancel() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, {
            if (it != null) println("BaseCoRoutineUseCase: $it")
        }, {
            job?.cancelAndJoin()
        })
    }
}