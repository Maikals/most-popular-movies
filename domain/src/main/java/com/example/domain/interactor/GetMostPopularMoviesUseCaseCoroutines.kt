package com.example.domain.interactor

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import javax.inject.Inject


class GetMostPopularMoviesUseCaseCoroutines @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository) {


    suspend fun coroutinesTest(params: MostPopularMoviesParams): MovieListEntity {
        return async(CommonPool) {
            mostPopularMoviesRepository.getMostPopularMovies(params.page)
        }.await()
    }
}