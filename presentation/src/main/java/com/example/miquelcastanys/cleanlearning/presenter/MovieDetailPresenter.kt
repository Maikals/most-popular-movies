package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MovieEntity
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.MovieDetailViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MovieDetailPresentationMapper
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.movieDetail.MovieDetailView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

@PerFragment
class MovieDetailPresenter @Inject constructor(private val searchMoviesUseCase: GetSearchMoviesUseCase) : Presenter {

    @Inject
    lateinit var view: MovieDetailView

    fun start() {
        view.showProgressBar(true)
    }

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {

    }


    fun manageResult(movieViewEntity: MovieDetailViewEntity) {
        view.setDetail(movieViewEntity)
        view.hideEmptyView()
        view.showRecyclerView()
        view.setLoadingState(false)
        view.showProgressBar(false)
    }

    fun manageEmptyList() {

        view.showProgressBar(false)
        view.hideRecyclerView()
        view.showEmptyView()
        view.setLoadingState(false)
    }

    class MoviesListObserver(private val presenter: MovieDetailPresenter) : DisposableObserver<MovieEntity>() {
        override fun onComplete() {
        }

        override fun onNext(movieEntity: MovieEntity) {
            presenter.manageResult(MovieDetailPresentationMapper.convertTo(movieEntity))
        }

        override fun onError(e: Throwable) =
                presenter.manageEmptyList()

    }

}