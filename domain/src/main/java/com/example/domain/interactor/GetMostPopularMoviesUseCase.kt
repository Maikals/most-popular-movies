package com.example.domain.interactor

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.MostPopularMoviesRepository
import io.reactivex.Single
import javax.inject.Inject


class GetMostPopularMoviesUseCase @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository,
                                                      override val postExecutionThread: PostExecutionThread) : UseCase<MovieListEntity, MostPopularMoviesParams> {

    override fun buildUseCaseObservable(params: MostPopularMoviesParams): Single<MovieListEntity> {
        return this.mostPopularMoviesRepository.getMostPopularMovies(params.page)
    }
}