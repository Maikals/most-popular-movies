package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.util.Log
import com.example.data.exeptions.ExceptionManager


abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onResume() {
        super.onResume()
        ExceptionManager.onErrorReceived = {
            showErrorMessage(it)
        }
    }

    @CallSuper
    open fun showErrorMessage(message: String) {
        Log.d("SHOW_ERROR", message)
    }

    protected abstract fun setupFragmentComponent()

}