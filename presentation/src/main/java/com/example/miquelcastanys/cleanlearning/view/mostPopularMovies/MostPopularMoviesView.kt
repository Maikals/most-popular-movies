package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity


interface MostPopularMoviesView {
    fun setItems(moviesList: List<BaseListViewEntity>)

    fun showProgressBar(show: Boolean)

    fun showRecyclerView()

    fun hideRecyclerView()

    fun hideEmptyView()

    fun showEmptyView()

    fun setLoadingState(state: Boolean)

    fun restartListAnimation()

}