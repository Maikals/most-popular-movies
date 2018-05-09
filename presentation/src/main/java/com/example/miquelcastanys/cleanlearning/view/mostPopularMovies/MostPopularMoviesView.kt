package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity


interface MostPopularMoviesView {
    fun setItems(moviesList: List<BaseListEntity>)

    fun showProgressBar(show: Boolean)

    fun showRecyclerView()

    fun hideRecyclerView()

    fun hideEmptyView()

    fun showEmptyView()

    fun stopRefreshLayout()
}