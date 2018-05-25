package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity
import com.example.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

interface MostPopularMoviesUseCase<T> {

    val postExecutionThread: PostExecutionThread
    val disposables: CompositeDisposable
        get() = CompositeDisposable()

    interface Callback {
        fun onReceived(moviesListEntity: MovieListEntity)
        fun onError()
    }

    fun execute(page: Int, observer: DisposableObserver<T>) {
        addDisposable(buildUseCaseObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(
                        {
                            observer.onNext(it)
                        },
                        {
                            observer.onError(it)
                        }))
    }

    fun buildUseCaseObservable(page: Int): Single<T>

    fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

}