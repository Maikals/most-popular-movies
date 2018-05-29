package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

interface UseCase<T, Params> {

    val postExecutionThread: PostExecutionThread
    val disposables: CompositeDisposable

    fun execute(params: Params, observer: DisposableObserver<T>) {
        addDisposable(buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .doOnDispose { observer.dispose() }
                .retry(3)
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(
                        {
                            observer.onNext(it)
                        },
                        {
                            observer.onError(it)
                        }))
    }

    fun buildUseCaseObservable(params: Params): Single<T>

    fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

}