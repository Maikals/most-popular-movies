package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.os.Bundle
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity


class MostPopularMoviesActivity : BaseActivity() {

    override fun createFragmentAndSettingTAG() {
        currentFragment = MostPopularMoviesFragment.newInstance()
        currentTag = MostPopularMoviesFragment.TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
    }

}
