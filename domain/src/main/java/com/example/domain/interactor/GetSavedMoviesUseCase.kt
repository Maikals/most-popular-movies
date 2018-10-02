package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject

class GetSavedMoviesUseCase @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository) : BaseCoRoutineUseCase<MovieListEntity, Unit?>() {
    override suspend fun buildRepoCall(params: Unit?): MovieListEntity =
            mostPopularMoviesRepository.getSavedMostPopularMovies()
}
