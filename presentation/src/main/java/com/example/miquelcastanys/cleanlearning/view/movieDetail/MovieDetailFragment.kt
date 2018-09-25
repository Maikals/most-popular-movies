package com.example.miquelcastanys.cleanlearning.view.movieDetail

import android.content.Context
import android.os.Bundle
import com.example.miquelcastanys.cleanlearning.entities.MovieDetailViewEntity
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment


class MovieDetailFragment : BaseFragment(), MovieDetailView {

    companion object {
        const val TAG = "MovieDetailFragment"
        private const val EXTRA_MOVIE_ID = "extraMovieId"

        fun newInstance(movieId: String): BaseFragment =
                MovieDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_MOVIE_ID, movieId)
                    }
                }
    }

    override fun setupFragmentComponent() {

    }

    override fun setDetail(movie: MovieDetailViewEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingState(state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restartListAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun provideContext(): Context = context!!
}