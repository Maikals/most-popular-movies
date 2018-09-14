package com.example.domain.interactor

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import javax.inject.Inject


class GetMostPopularMoviesUseCaseCoroutines @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository) {

    var job: Job? = null

    suspend fun coroutinesTest(params: MostPopularMoviesParams, block: (MovieListEntity) -> Unit) {
        cancel()
        job = launchAsync{
            asyncAwait {
                block(mostPopularMoviesRepository.getMostPopularMovies(params.page))
            }
        }
    }

    fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(UI) { block() }
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