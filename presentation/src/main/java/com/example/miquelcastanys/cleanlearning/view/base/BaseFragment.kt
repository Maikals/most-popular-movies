package com.example.miquelcastanys.cleanlearning.view.base

import android.os.Bundle
import android.support.v4.app.Fragment


abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    protected abstract fun setupFragmentComponent()

}