package com.example.domain.base

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


abstract class BaseUseCase<T, Params>(private val postExecutionThread: PostExecutionThread) {

    private var disposables: CompositeDisposable = CompositeDisposable()
    get() =
        if(field.isDisposed)
            CompositeDisposable()
        else field


    suspend fun execute(params: Params, observer: DisposableObserver<T>) {
        addDisposable(buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler())
                .doOnDispose { observer.dispose() }
                .subscribe(
                        {
                            observer.onNext(it)
                        },
                        {
                            observer.onError(it)
                        }))

    }

    private fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    abstract fun buildUseCaseObservable(params: Params): Single<T>

}