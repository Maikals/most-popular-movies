package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity
import com.example.domain.entity.SearchMoviesParams
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.SearchMoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(private val searchMoviesRepository: SearchMoviesRepository, override val postExecutionThread: PostExecutionThread) : UseCase<MovieListEntity, SearchMoviesParams> {

    override fun buildUseCaseObservable(params: SearchMoviesParams): Single<MovieListEntity> {
        return searchMoviesRepository.getSearchMovies(params.searchText, params.page)
    }

}