package com.example.domain.interactor

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.MovieListEntity
import com.example.domain.entity.SearchMoviesParams
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.SearchMoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(private val searchMoviesRepository: SearchMoviesRepository,
                                                 postExecutionThread: PostExecutionThread)
    : BaseUseCase<MovieListEntity, SearchMoviesParams>(postExecutionThread) {

    override fun buildUseCaseObservable(params: SearchMoviesParams): Single<MovieListEntity> =
            searchMoviesRepository.getSearchMovies(params.searchText, params.page)
}