package com.example.domain.base

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.BaseParams
import com.example.domain.exceptions.CustomException
import kotlinx.coroutines.*

abstract class BaseCoRoutineUseCase<T : BaseEntity, Params : BaseParams> {

    private var job: Job? = null
    private var asyncJob: Job? = null

    companion object {
        private const val TAG: String = "BaseCoRoutineUseCase"
    }

    fun executeAsync(params: Params, block: suspend CoroutineScope.(T) -> Unit,
                     blockError: (CustomException?) -> Unit) {
        job = launchAsync({

            val result = buildRepoCall(params)
            launchUI { block(result) }
        }, blockError)

    }

//    fun executeBlocking(params: Params, block: suspend CoroutineScope.(T) -> Unit,
//                        blockError: (CustomException?) -> Unit) {
//        job = runBlocking(Dispatchers.Default) {
//            val result = buildRepoCall(params)
//            launchUI { block(result) }
//        }
//    }

    abstract suspend fun buildRepoCall(params: Params): T

    private fun createExceptionHandler(blockError: (CustomException?) -> Unit) =
            CoroutineExceptionHandler { _, e ->
                launchUI { blockError(CustomException(e)) }
            }

    /**
     * Launch a coroutine in a new Thread.
     * @param block The block that will be executed inside coroutine
     */
    private fun launchAsync(block: suspend CoroutineScope.() -> Unit, blockError: (CustomException?) -> Unit): Job =
            GlobalScope.async(Dispatchers.Default + createExceptionHandler(blockError)) {
                block()
            }

    /**
     * Launch a coroutine in the main thread.
     * @param block The block that will be executed inside coroutine
     */
    private fun launchUI(block: suspend CoroutineScope.() -> Unit): Job =
            GlobalScope.launch(Dispatchers.Main) {
                block()
            }

    /**
     * Cancels the current job execution.
     */
    fun cancel() {
        job?.cancel()
    }
}