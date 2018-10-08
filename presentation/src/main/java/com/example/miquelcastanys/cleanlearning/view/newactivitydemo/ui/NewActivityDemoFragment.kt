package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.databinding.NewActivityDemoFragmentBinding
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesNewModule
import com.example.miquelcastanys.cleanlearning.observe
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.new_activity_demo_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class NewActivityDemoFragment : BaseFragment() {

    override fun setupFragmentComponent() {
        MostPopularMoviesApplication
                .applicationComponent
                .plus(BaseFragmentModule(context!!), MostPopularMoviesNewModule(this))
                .inject(this)
    }

    companion object {
        const val TAG = "NewActivityDemoFragment"
        fun newInstance() = NewActivityDemoFragment()
    }

    private lateinit var binding: NewActivityDemoFragmentBinding

    private val newActivityDemoViewModel: NewActivityDemoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_activity_demo_fragment, container, false) as NewActivityDemoFragmentBinding
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        binding.viewModel = newActivityDemoViewModel
        button_request_movies_new_fragment.setOnClickListener {
            checkReachAbility { result ->
                if (result) {
                    newActivityDemoViewModel.getMovies()
                } else {
                    Toast.makeText(activity, "You need internet to retrieve results!", Toast.LENGTH_SHORT).show()
                }
            }
            startTimerVue()
        }

        onDataReceived()
        onCounterReceived()
        stopTimerVue()
        Toast.makeText(activity, "INTERNET: ${isInternetReachAble()}", Toast.LENGTH_LONG).show()
    }

    private fun onCounterReceived() {
        observe(newActivityDemoViewModel.requestCounter) {
            counter_request.text = it.toString()
        }
    }

    private fun onDataReceived() {
        observe(newActivityDemoViewModel.onDataReceived) {
            message.text = it.toString()
        }
    }
}
