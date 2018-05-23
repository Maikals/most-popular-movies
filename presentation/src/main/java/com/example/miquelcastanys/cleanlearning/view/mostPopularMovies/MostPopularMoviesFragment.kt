package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.adapters.MostPopularMovieListAdapter
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.enumerations.EmptyViewEnumeration
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesModule
import com.example.miquelcastanys.cleanlearning.presenter.MostPopularMoviesPresenter
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_most_popular_movies.*
import javax.inject.Inject

class MostPopularMoviesFragment : BaseFragment(), MostPopularMoviesView {
    override var isSearching: Boolean = false
    private var searchAction: MenuItem? = null

    @Inject
    lateinit var presenter: MostPopularMoviesPresenter

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
                if (!presenter.isLastPage && !loading) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                        loading = true
                        presenter.loadMoreElements()
                    }
                }
            } else if (linearLayoutManager.findLastVisibleItemPosition() == mostPopularMoviesRV.adapter.itemCount - 1
                    && !presenter.isLastPage) {
                presenter.loadMoreElements()
                loading = true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_most_popular_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRefreshLayoutBehaviour()
        setRecyclerView()
        setEmptyView()
        setHasOptionsMenu(true)
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                searchMovie(newText)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        searchAction = menu?.findItem(R.id.action_search)
        searchAction?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                searchClosed()
                getMostPopularMovies()
                return true
            }

        })
        super.onPrepareOptionsMenu(menu)
    }

    override fun setupFragmentComponent() {
        MostPopularMoviesApplication
                .applicationComponent
                .plus(BaseFragmentModule(context!!), MostPopularMoviesModule(this))
                .inject(this)
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

    private fun setRefreshLayoutBehaviour() =
        swipeRefreshLayout.setOnRefreshListener {
            loading = true
            if (!isSearching) {
                (mostPopularMoviesRV.adapter as? MostPopularMovieListAdapter)?.restartLastPosition()
            }
            presenter.start()
        }

    private fun attachScrollListener() =
        mostPopularMoviesRV.addOnScrollListener(onScrollListener)

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

    override fun setItems(moviesList: List<BaseListViewEntity>) {
        if (mostPopularMoviesRV.adapter == null) mostPopularMoviesRV.adapter = MostPopularMovieListAdapter(moviesList)

        mostPopularMoviesRV.adapter.notifyDataSetChanged()
    }

    override fun setLoadingState(state: Boolean) {
        loading = state
    }

    private fun unattachScrollListener() =
        mostPopularMoviesRV.removeOnScrollListener(onScrollListener)

    fun searchMovie(newText: String?) {
        presenter.searchMovieByText(newText, refreshList = true)
    }


    fun searchClosed() {
        presenter.isSearching = false
    }

    fun getMostPopularMovies() {
        presenter.start()
    }
}

