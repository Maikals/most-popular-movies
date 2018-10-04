package com.example.miquelcastanys.cleanlearning.view.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View


abstract class BaseFragment : Fragment() {

    private var baseInterface: BaseActivityFragmentInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
    }

    protected abstract fun setupFragmentComponent()

    protected abstract fun createViewModel()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        baseInterface = context as? BaseActivityFragmentInterface
    }

    fun getToolbar(): Toolbar? = baseInterface?.getToolbar()

    fun stopTimerVue() = baseInterface?.stopTimerVue()

    fun startTimerVue() = baseInterface?.startTimerVue()

    fun checkReachAbility(block: (Boolean) -> Unit) =
            baseInterface?.checkReachAbility(block)

    fun isInternetReachAble(): Boolean? = baseInterface?.isInternetReachable()
}