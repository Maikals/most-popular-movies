package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity


class MostPopularMoviesActivity : BaseActivity() {

    private var searchAction: MenuItem? = null

    override fun createFragmentAndSettingTAG() {
        currentFragment = MostPopularMoviesFragment.newInstance()
        currentTag = MostPopularMoviesFragment.TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                (currentFragment as? MostPopularMoviesFragment)?.searchMovie(newText)
                return true
            }

        })

        return true
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        searchAction = menu.findItem(R.id.action_search)
        searchAction?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                (currentFragment as? MostPopularMoviesFragment)?.searchClosed()
                (currentFragment as? MostPopularMoviesFragment)?.getMostPopularMovies()
                return true
            }

        })
        return super.onPrepareOptionsMenu(menu)
    }

}
