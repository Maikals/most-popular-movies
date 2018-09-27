package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.interfaces.MostPopularMoviesActivityFragmentInterface
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*


class MostPopularMoviesActivity : BaseActivity(), MostPopularMoviesActivityFragmentInterface {

    override fun createFragmentAndSettingTAG() {
        currentFragment = MostPopularMoviesFragment.newInstance()
        currentTag = MostPopularMoviesFragment.TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
    }

    override fun onConnectivityChanges(isConnected: Boolean) {
        super.onConnectivityChanges(isConnected)
        println("INTERNET STATE AFTER PING: $isConnected")
    }

    override fun getToolbar(): Toolbar = toolbar

}
