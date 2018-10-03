package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.domain.base.Log
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.interfaces.MostPopularMoviesActivityFragmentInterface
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*


class MostPopularMoviesActivity : BaseActivity(), MostPopularMoviesActivityFragmentInterface {

    val TAG: String = "MostPopularMoviesActivity"
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
        Log.d(TAG, "INTERNET STATE AFTER PING: $isConnected")
    }

    override fun onConnectivityChangesVUE(isConnected: Boolean) {
        super.onConnectivityChangesVUE(isConnected)
        Log.d(TAG, "VUE STATE AFTER PING: $isConnected")
    }

    override fun onConnectivityChangesFUSE(isConnected: Boolean) {
        super.onConnectivityChangesFUSE(isConnected)
        Log.d(TAG, "FUSE STATE AFTER PING: $isConnected")
    }

    override fun getToolbar(): Toolbar = toolbar


}
