package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.domain.base.Log
import com.example.miquelcastanys.cleanlearning.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity(), BaseActivityFragmentInterface {

    protected var currentTag: String? = null
    protected var currentFragment: Fragment? = null



    override fun isInternetReachable(): Boolean = ReachAbilityManager.isBackOfficeReachable

    companion object {
        private const val CURRENT_FRAGMENT_TAG: String = "currentTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setToolbar()
        initializeFragmentAndTAG(savedInstanceState)
        beginTransaction()
    }

    override fun onResume() {
        super.onResume()

        ReachAbilityManager.setBackOfficeReachAbleListener {
            onConnectivityChanges(it)
        }

        ReachAbilityManager.setReachAbleListenerVUE {
            onConnectivityChangesVUE(it)
        }

        ReachAbilityManager.setReachAbleListenerFUSE {
            onConnectivityChangesFUSE(it)
        }

    }


    @CallSuper
    open fun onConnectivityChanges(isConnected: Boolean) {
        //set a general param.
        Log.d("BaseActvity: ", "onConnectivityChanges reached with isConnected: $isConnected")
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


    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun getToolbar(): Toolbar {
        return toolbar
    }

    override fun stopTimerVue() {
        ReachAbilityManager.stopTimerVUE()
    }

    override fun startTimerVue() {
        ReachAbilityManager.startTimerVUE()
    }

    override fun checkReachAbility(block: (Boolean) -> Unit) {
        ReachAbilityManager.checkBackOfficeReachAbility(block)
    }
}