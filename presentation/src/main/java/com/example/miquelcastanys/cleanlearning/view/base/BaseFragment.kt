package com.example.miquelcastanys.cleanlearning.view.base

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import org.koin.standalone.KoinComponent


abstract class BaseFragment : Fragment(), KoinComponent {

    private var baseInterface: BaseActivityFragmentInterface? = null

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