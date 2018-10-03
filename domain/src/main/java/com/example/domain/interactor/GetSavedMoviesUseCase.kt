package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject

class GetSavedMoviesUseCase @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository)
    : BaseCoRoutineUseCase<MovieListEntity, EmptyParams>() {
    override suspend fun buildRepoCall(params: EmptyParams): MovieListEntity =
            mostPopularMoviesRepository.getSavedMostPopularMovies()
}
