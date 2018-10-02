package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity(), BaseActivityFragmentInterface {
    protected var currentTag: String? = null
    protected var currentFragment: Fragment? = null
    protected var isConnected: Boolean = true

    override fun isInternetReachable(): Boolean = isConnected

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

    override fun onResume() {
        super.onResume()


        ReachAbilityManager.setBackOfficeReachAbleListener("vetscanvue.abaxis.com") {
            onConnectivityChanges(it)
        }

        ReachAbilityManager.setFirstDeviceReachAbleListener("192.168.1.20") {
            onConnectivityChangesVUE(it)
        }

        ReachAbilityManager.setSecondDeviceReachAbleListener("192.168.1.151") {
            onConnectivityChangesFUSE(it)
        }

    }


    @CallSuper
    open fun onConnectivityChanges(isConnected: Boolean) {
        //set a general param.
        this.isConnected = isConnected
    }

    @CallSuper
    open fun onConnectivityChangesVUE(isConnected: Boolean) {
        //set a general param.
    }

    @CallSuper
    open fun onConnectivityChangesFUSE(isConnected: Boolean) {
        //set a general param.
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

    override fun getToolbar(): Toolbar {
        return toolbar
    }
}