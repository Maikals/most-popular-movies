package com.example.domain.interactor

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import javax.inject.Inject


class GetMostPopularMoviesUseCaseCoroutines @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository) {


    suspend fun coroutinesTest(params: MostPopularMoviesParams): MovieListEntity {
        var result: MovieListEntity? = null
        launchAsync{
            asyncAwait {
                result = mostPopularMoviesRepository.getMostPopularMovies(params.page)
            }
        }
        return result ?: MovieListEntity(-1, -1, listOf())
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

}