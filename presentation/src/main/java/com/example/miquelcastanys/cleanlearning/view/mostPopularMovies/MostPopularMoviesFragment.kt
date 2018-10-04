package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.adapters.MostPopularMovieListAdapter
import com.example.miquelcastanys.cleanlearning.buildViewModel
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.enumerations.EmptyViewEnumeration
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesModule
import com.example.miquelcastanys.cleanlearning.interfaces.MostPopularMoviesActivityFragmentInterface
import com.example.miquelcastanys.cleanlearning.observe
import com.example.miquelcastanys.cleanlearning.presenter.MostPopularMoviesPresenter
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.NewActivityDemo
import kotlinx.android.synthetic.main.fragment_most_popular_movies.*
import javax.inject.Inject


class MostPopularMoviesFragment : BaseFragment(), MostPopularMoviesView {

    @Inject
    lateinit var factory: MostPopularModelFactory

    @Inject
    lateinit var presenter: MostPopularMoviesPresenter

    lateinit var viewModel: MostPopularMoviesViewModel

    private var searchAction: MenuItem? = null
    var isSearchExpanded: Boolean = false

    private var mostPopularMoviesActivityFragmentInterface: MostPopularMoviesActivityFragmentInterface? = null

    companion object {
        const val TAG = "MostPopularMoviesFragment"
        fun newInstance(): MostPopularMoviesFragment = MostPopularMoviesFragment()
    }

    private val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,
            false)

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 && isInternetReachable()) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                if (!presenter.isLastPage) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                        viewModel.getMostPopularMovies()
                    }
                }
            } else if (linearLayoutManager.findLastVisibleItemPosition() == mostPopularMoviesRV.adapter?.itemCount!! - 1
                    && !presenter.isLastPage) {
                viewModel.getMostPopularMovies()
            }
        }
    }

    private fun isInternetReachable() =
            mostPopularMoviesActivityFragmentInterface?.isInternetReachable() ?: false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_most_popular_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRefreshLayoutBehaviour()
        setRecyclerView()
        setEmptyView()
        setHasOptionsMenu(true)
        //createViewModel()
        getData()

        if (mostPopularMoviesRV.adapter == null)
            mostPopularMoviesRV.adapter = MostPopularMovieListAdapter(viewModel.mostPopularMovies.value as List<BaseListViewEntity>)

        observe(viewModel.onDataReceived) { result ->
            result?.let {
                if (it) {
                    showProgressBar(false)
                    showRecyclerView()
                    mostPopularMoviesRV.adapter!!.notifyDataSetChanged()
                } else {
                    showProgressBar(false)
                }
            }
        }

        presenter.start()

        open_new_activity_button.setOnClickListener {
            startActivity(Intent(activity, NewActivityDemo::class.java))
        }
    }

    override fun createViewModel() {
        viewModel = buildViewModel(factory, MostPopularMoviesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mostPopularMoviesActivityFragmentInterface = context as? MostPopularMoviesActivityFragmentInterface
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (isSearchExpanded) {
                    stopScroll()
                    searchMovie(newText)
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        searchAction = menu?.findItem(R.id.action_search)
        searchAction?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                isSearchExpanded = true
                animateSearchToolbar(1, true, true)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                isSearchExpanded = false
                animateSearchToolbar(1, false, false)
                stopScroll()
                searchClosed()
                getMostPopularMovies()
                return true
            }

        })
        super.onPrepareOptionsMenu(menu)
    }

    private fun stopScroll() {
        mostPopularMoviesRV.stopScroll()
    }

    override fun setupFragmentComponent() {
        MostPopularMoviesApplication
                .applicationComponent
                .plus(BaseFragmentModule(context!!), MostPopularMoviesModule(this))
                .inject(this)
    }

    override fun showProgressBar(show: Boolean) {
        swipeRefreshLayout?.let {
            if (swipeRefreshLayout.isRefreshing) {
                if (!show)
                    swipeRefreshLayout.isRefreshing = false
            } else progressBar?.visibility = if (show) View.VISIBLE else View.GONE
        }
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
                if (!presenter.isSearching) {
                    restartListAnimation()
                }
                getData()
            }

    private fun getData() {
        if (isInternetReachable())
            viewModel.getMostPopularMovies(true)
        else viewModel.getSavedMovies()
    }

    override fun restartListAnimation() {
        (mostPopularMoviesRV?.adapter as? MostPopularMovieListAdapter)?.restartLastPosition()
    }

    private fun attachScrollListener() =
            mostPopularMoviesRV?.addOnScrollListener(onScrollListener)

    override fun showRecyclerView() {
        mostPopularMoviesRV?.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        mostPopularMoviesRV?.visibility = View.GONE
    }

    override fun hideEmptyView() {
        emptyView?.visibility = View.GONE
    }

    override fun showEmptyView() {
        emptyView?.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
        unattachScrollListener()
    }

    override fun setItems(moviesList: List<BaseListViewEntity>) {

//        mostPopularMoviesRV?.let {
//            if (mostPopularMoviesRV.adapter == null) mostPopularMoviesRV.adapter = MostPopularMovieListAdapter(moviesList)
//            mostPopularMoviesRV.adapter.notifyDataSetChanged()
//        }
    }

    private fun unattachScrollListener() =
            mostPopularMoviesRV?.removeOnScrollListener(onScrollListener)

    fun searchMovie(newText: String?) {
        presenter.searchMovieByText(newText, refreshList = true)
    }

    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {

        val toolbar = mostPopularMoviesActivityFragmentInterface?.getToolbar()
        toolbar?.let {
            toolbar.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.white))

            if (show) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val createCircularReveal = createRevealAnimationReveal(toolbar, containsOverflow, numberOfMenuIcon)
                    createCircularReveal.duration = 250
                    createCircularReveal.start()
                } else {
                    searchRevealAnimationPreLollipop(toolbar)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val createCircularReveal = createCircularAnimationClose(toolbar, containsOverflow, numberOfMenuIcon)
                    createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            toolbar.setBackgroundColor(getThemeColor(context!!, R.attr.colorPrimary))
                        }
                    })
                    createCircularReveal.start()
                } else {
                    searchCloseAnimationPreLollipop(toolbar)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createRevealAnimationReveal(toolbar: Toolbar, containsOverflow: Boolean, numberOfMenuIcon: Int): Animator {
        val width = toolbar.width -
                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
                else 0) -
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) *
                numberOfMenuIcon / 2
        return ViewAnimationUtils.createCircularReveal(toolbar,
                if (isRtl(resources)) toolbar.width - width
                else width,
                toolbar.height / 2,
                0.0f,
                width.toFloat())
    }

    private fun searchRevealAnimationPreLollipop(toolbar: Toolbar) {
        val translateAnimation = TranslateAnimation(0.0f,
                0.0f,
                -toolbar.height.toFloat(),
                0.0f)
        translateAnimation.duration = 220
        toolbar.clearAnimation()
        toolbar.startAnimation(translateAnimation)
    }

    private fun searchCloseAnimationPreLollipop(toolbar: Toolbar) {
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        val translateAnimation = TranslateAnimation(0.0f,
                0.0f,
                0.0f,
                -toolbar.height.toFloat())
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(translateAnimation)
        animationSet.duration = 220
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) =
                    toolbar.setBackgroundColor(getThemeColor(context!!, R.attr.colorPrimary))

            override fun onAnimationRepeat(animation: Animation) {}
        })
        toolbar.startAnimation(animationSet)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCircularAnimationClose(toolbar: Toolbar, containsOverflow: Boolean, numberOfMenuIcon: Int): Animator {
        val width = toolbar.width -
                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
        val createCircularReveal = ViewAnimationUtils.createCircularReveal(toolbar,
                if (isRtl(resources)) toolbar.width - width else width,
                toolbar.height / 2,
                width.toFloat(),
                0.0f)
        createCircularReveal.duration = 250
        return createCircularReveal
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun isRtl(resources: Resources): Boolean {
        return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    private fun getThemeColor(context: Context, id: Int): Int {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(intArrayOf(id))
        val result = a.getColor(0, 0)
        a.recycle()
        return result
    }

    fun searchClosed() {
        presenter.isSearching = false
        presenter.cancelSearch()
    }

    fun getMostPopularMovies() {
        presenter.start()
    }

    fun showErrorMessage(message: String) {
        Snackbar.make(activity_most_popular_movies_container, message, Snackbar.LENGTH_SHORT).show()
        swipeRefreshLayout?.isRefreshing = false
    }
}

