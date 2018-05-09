package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.adapters.MostPopularMovieListAdapter
import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity
import com.example.miquelcastanys.cleanlearning.entities.enumerations.EmptyViewEnumeration
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_most_popular_movies.*


class MostPopularMoviesFragment : BaseFragment(), MostPopularMoviesView {

    companion object {
        const val TAG = "MostPopularMoviesFragment"
        fun newInstance() : MostPopularMoviesFragment = MostPopularMoviesFragment()
    }

    private val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,
            false)
    private var loading: Boolean = false

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy > 0) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                /*if (!presenter?.isLastPage!! && !loading) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                        loading = true
                        presenter?.getTvShowsList()
                    }
                }*/
            }/* else if (linearLayoutManager.findLastVisibleItemPosition() == tvShowsListRV.adapter.itemCount - 1
                    && !presenter?.isLastPage!!) {
                presenter?.getTvShowsList()
                loading = true
            }*/
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_most_popular_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRefreshLayoutBehaviour()
        setRecyclerView()
        setEmptyView()
    }

    override fun showProgressBar(show: Boolean) {
        if (swipeRefreshLayout.isRefreshing) {
            if (!show)
                swipeRefreshLayout.isRefreshing = false
        } else progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setEmptyView() {
        emptyView.fillViews(EmptyViewEnumeration.MOVIES_LIST_EMPTY_VIEW)
    }

    private fun setRecyclerView() {
        mostPopularMoviesRV.layoutManager = linearLayoutManager
        attachScrollListener()
    }

    private fun setRefreshLayoutBehaviour() {
        swipeRefreshLayout.setOnRefreshListener {
            //call service
        }
    }

    private fun attachScrollListener() {
        mostPopularMoviesRV.addOnScrollListener(onScrollListener)
    }

    override fun showRecyclerView() {
        mostPopularMoviesRV.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        mostPopularMoviesRV.visibility = View.GONE
    }

    override fun hideEmptyView() {
        emptyView.visibility = View.GONE

    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unattachScrollListener()
    }

    override fun setItems(moviesList: List<BaseListEntity>) {
        if (mostPopularMoviesRV.adapter == null) mostPopularMoviesRV.adapter = MostPopularMovieListAdapter(moviesList)
        mostPopularMoviesRV.adapter.notifyDataSetChanged()
    }

    private fun unattachScrollListener() {
        mostPopularMoviesRV.removeOnScrollListener(onScrollListener)
    }
}