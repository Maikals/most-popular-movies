package com.example.miquelcastanys.cleanlearning.view.movieDetail

import android.content.Context
import com.example.miquelcastanys.cleanlearning.entities.MovieDetailViewEntity


interface MovieDetailView {
    fun setDetail(movie: MovieDetailViewEntity)

    fun showProgressBar(show: Boolean)

    fun showRecyclerView()

    fun hideRecyclerView()

    fun hideEmptyView()

    fun showEmptyView()

    fun setLoadingState(state: Boolean)

    fun restartListAnimation()

    fun provideContext(): Context

}