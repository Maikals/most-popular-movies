package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.MostPopularMoviesRepository
import io.reactivex.Single
import javax.inject.Inject


class GetMostPopularMoviesUseCase @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository,
                                                      override val postExecutionThread: PostExecutionThread) : MostPopularMoviesUseCase<MovieListEntity> {

    override fun buildUseCaseObservable(page: Int): Single<MovieListEntity> {
        return this.mostPopularMoviesRepository.getMostPopularMovies(page)
    }
}