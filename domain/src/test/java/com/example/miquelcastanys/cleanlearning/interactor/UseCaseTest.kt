package com.example.miquelcastanys.cleanlearning.interactor

import com.example.domain.base.BaseUseCase
import com.example.domain.executor.PostExecutionThread
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

    private var useCase: UseCaseTestClass? = null

    private lateinit var testObserver: TestDisposableObserver<Any>

    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread
    @Mock
    private lateinit var single: Single<Any>

    @Before
    fun setUp() {
        this.useCase = UseCaseTestClass(mockPostExecutionThread, single)
        this.testObserver = TestDisposableObserver()
        given(mockPostExecutionThread.getScheduler()).willReturn(TestScheduler())
    }

    @Test
    fun testBuildUseCaseObservableReturnCorrectResult() {
        useCase?.execute(Params, testObserver)

        assert(testObserver.valuesCount == 0)
    }

    @Test
    fun testSubscriptionWhenExecutingUseCase() {
        useCase?.execute(Params, testObserver)
        useCase?.dispose()
        assert(testObserver.isDisposed)
    }


    private class UseCaseTestClass(postExecutionThread: PostExecutionThread,
                                   val single: Single<Any>) : BaseUseCase<Any, Params>(postExecutionThread) {

        override fun buildUseCaseObservable(params: Params): Single<Any> {
            return single
        }

    }

    private class TestDisposableObserver<T> : DisposableObserver<T>() {
        var valuesCount = 0

        override fun onNext(value: T) {
            valuesCount++
        }

        override fun onError(e: Throwable) {
            // no-op by default.
        }

        override fun onComplete() {
            // no-op by default.
        }
    }

    private object Params

}
