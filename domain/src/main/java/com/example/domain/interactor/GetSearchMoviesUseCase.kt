package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.MovieListEntity
import com.example.domain.entity.SearchMoviesParams
import com.example.domain.repository.SearchMoviesRepository

class GetSearchMoviesUseCase constructor(private val searchMoviesRepository: SearchMoviesRepository)

    : BaseCoRoutineUseCase<MovieListEntity, SearchMoviesParams>() {
    override suspend fun buildRepoCall(params: SearchMoviesParams): MovieListEntity =
            searchMoviesRepository.getSearchMovies(params.searchText, params.page)
}