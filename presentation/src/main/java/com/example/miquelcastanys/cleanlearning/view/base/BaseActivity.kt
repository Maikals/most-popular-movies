package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {
    protected var currentTag: String? = null
    protected var currentFragment: Fragment? = null

    companion object {
        private const val CURRENT_FRAGMENT_TAG: String = "currentTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setToolbar()
        setupActivityComponent()
        initializeFragmentAndTAG(savedInstanceState)
        beginTransaction()
    }

    private fun showConnectivityBackofficeToast(isConnectedToInternet: Boolean) {
        Toast.makeText(this, if (isConnectedToInternet) "Connection Backoffice OK" else "Connection Backoffice KO", Toast.LENGTH_SHORT).show()
    }

    private fun showConnectivityGoogleToast(isConnectedToInternet: Boolean) {
        Toast.makeText(this, if (isConnectedToInternet) "Connection Google OK" else "Connection Google KO", Toast.LENGTH_SHORT).show()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }


    private fun initializeFragmentAndTAG(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CURRENT_FRAGMENT_TAG)) {
            createFragmentAndSettingTAG()
        } else {
            currentTag = savedInstanceState.getString(CURRENT_FRAGMENT_TAG)
            currentFragment = supportFragmentManager.getFragment(savedInstanceState, currentTag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        supportFragmentManager.putFragment(outState, currentTag, currentFragment)
        outState?.putString(CURRENT_FRAGMENT_TAG, currentTag)
        super.onSaveInstanceState(outState)
    }

    private fun beginTransaction() =
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment, currentTag).commit()

    abstract fun createFragmentAndSettingTAG()

    private fun setupActivityComponent() {
        MostPopularMoviesApplication.applicationComponent.inject(this)
    }

    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}