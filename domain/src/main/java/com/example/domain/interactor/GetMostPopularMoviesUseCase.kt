package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.MostPopularMoviesRepository
import io.reactivex.Observable
import io.reactivex.observers.DefaultObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class GetMostPopularMoviesUseCase @Inject constructor(private val mostPopularMoviesRepository: MostPopularMoviesRepository,
                                                      override val postExecutionThread: PostExecutionThread) : MostPopularMoviesUseCase {
    override fun execute(page: Int, observer: DefaultObserver<Any>) {

        buildUseCaseObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(
                        {
                            observer.onNext(it)
                        },
                        {
                            observer.onError(it)
                        })
    }

    private fun buildUseCaseObservable(page: Int): Observable<MovieListEntity> {
        return this.mostPopularMoviesRepository.getMostPopularMovies(page)
    }
}