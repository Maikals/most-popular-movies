package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository


class GetMostPopularMoviesUseCase(private val mostPopularMoviesRepository: MostPopularMoviesRepository)
    : BaseCoRoutineUseCase<MovieListEntity, MostPopularMoviesParams>() {

    override suspend fun buildRepoCall(params: MostPopularMoviesParams): MovieListEntity =
            mostPopularMoviesRepository.getMostPopularMovies(params.page)
}