package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseActivityFragmentInterface {
    protected var currentTag: String? = null
    protected var currentFragment: Fragment? = null
    protected var isConnected: Boolean = false

    @Inject
    lateinit var reachAbilityManager: ReachAbilityManager

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
        // SET A GLOBAL STATE VAR TO MANAGE CHANGES IN DEVICES / BO
        reachAbilityManager.setBackOfficeReachAbleListener {
            onConnectivityChanges(it)
        }

        reachAbilityManager.setReachAbleListenerVUE {
            onConnectivityChangesVUE(it)
        }

        reachAbilityManager.setReachAbleListenerFUSE {
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
            currentFragment = supportFragmentManager.getFragment(savedInstanceState, currentTag!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        supportFragmentManager.putFragment(outState!!, currentTag!!, currentFragment!!)
        outState.putString(CURRENT_FRAGMENT_TAG, currentTag)
        super.onSaveInstanceState(outState)
    }

    private fun beginTransaction() =
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment!!, currentTag).commit()

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