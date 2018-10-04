package com.example.domain.base

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.BaseParams
import com.example.domain.exceptions.CustomException
import com.example.domain.exceptions.ExceptionType
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

abstract class BaseCoRoutineUseCase<T : BaseEntity, Params : BaseParams> {

    private var job: Job? = null

    companion object {
        private const val TAG: String = "BaseCoRoutineUseCase"
    }

    fun execute(params: Params, block: suspend CoroutineScope.(T) -> Unit,
                blockError: (CustomException?) -> Unit) {
        job = launchAsync {
            async({
                val result = buildRepoCall(params)
                launchMain {
                    block(result)
                }
            }, {
                blockError(it)
            })
        }
    }

    abstract suspend fun buildRepoCall(params: Params): T

    /**
     * Launch a coroutine in the main thread.
     * @param block The block that will be executed inside coroutine
     */
    private fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job =
            GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, {
                if (it != null) Log.e(TAG, it.message ?: "", it)
            }, {
                block()
            })

    private fun launchMain(block: suspend CoroutineScope.() -> Unit): Job =
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, {
                if (it != null) Log.e(TAG, it.message ?: "", it)
            }, {
                block()
            })

    /**
     * Launch an async coroutine and wait for the result.
     * @param block         The block that will be executed inside the async coroutine
     * @param blockError    The block that will be executed when the coroutine ends. If there's
     *                      some error, the block will have a throwable in it parameter.
     */
    private suspend fun <T> async(block: suspend CoroutineScope.() -> T, blockError: (CustomException?) -> T): Deferred<T> {
        return GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, {
            if (it != null) {
                Log.e(TAG, it.message ?: "", it)
                blockError(CustomException(it, ExceptionType.UNDEFINED))
            }
        }, {
            block()
        })
    }

    /**
     * Launch an async coroutine and wait for the result. This method doesn't have to be used if
     * the @block doesn't return a Deferred object.
     * @param block         The block that will be executed inside the async coroutine
     * @param blockError    The block that will be executed when the coroutine ends. If there's
     *                      some error, the block will have a throwable in it parameter.
     */
    private suspend fun <T : Deferred<T>> asyncAwait(block: suspend CoroutineScope.() -> T, blockError: (CustomException?) -> T): T {
        return async(block, blockError).await()
    }

    /**
     * Cancels the current job execution.
     */
    fun cancel() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, {
            if (it != null) Log.e(TAG, it.message ?: "", it)
        }, {
            job?.cancelAndJoin()
        })
    }
}