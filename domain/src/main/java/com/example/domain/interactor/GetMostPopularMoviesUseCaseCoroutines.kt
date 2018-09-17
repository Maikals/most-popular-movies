package com.example.domain.interactor

import com.example.domain.base.BaseCoroutineUseCase
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject


class GetMostPopularMoviesUseCaseCoroutines @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository)
    : BaseCoroutineUseCase<MovieListEntity, MostPopularMoviesParams>() {

    override fun buildRepoCall(params: MostPopularMoviesParams): MovieListEntity =
            mostPopularMoviesRepository.getMostPopularMovies(params.page)

}