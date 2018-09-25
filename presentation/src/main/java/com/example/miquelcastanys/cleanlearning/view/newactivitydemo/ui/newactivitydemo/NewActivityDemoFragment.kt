package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.newactivitydemo

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miquelcastanys.cleanlearning.R


class NewActivityDemoFragment : Fragment() {

    companion object {
        const val TAG = "NewActivityDemoFragment"
        fun newInstance() = NewActivityDemoFragment()
    }

    private lateinit var viewModel: NewActivityDemoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.new_activity_demo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewActivityDemoViewModel::class.java)
        // TODO: Use the ViewModel


    }

}
