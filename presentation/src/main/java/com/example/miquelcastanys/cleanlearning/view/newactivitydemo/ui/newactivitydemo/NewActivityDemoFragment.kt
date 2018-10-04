package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.newactivitydemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.buildViewModel
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesNewModule
import com.example.miquelcastanys.cleanlearning.observe
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.new_activity_demo_fragment.*
import javax.inject.Inject


class NewActivityDemoFragment : BaseFragment() {

    override fun setupFragmentComponent() {
        MostPopularMoviesApplication
                .applicationComponent
                .plus(BaseFragmentModule(context!!), MostPopularMoviesNewModule(this))
                .inject(this)
    }

    @Inject
    lateinit var factory: MostPopularNewModelFactory

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

        button_request_movies_new_fragment.setOnClickListener {
            checkReachAbility { result ->
                if (result) {
                    viewModel.getMovies()
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
        observe(viewModel.requestCounter) {
            counter_request.text = it.toString()
        }
    }

    private fun onDataReceived() {
        observe(viewModel.onDataReceived) {
            message.text = it.toString()
        }
    }

    override fun createViewModel() {
        viewModel = buildViewModel(factory, NewActivityDemoViewModel::class.java)
    }
}
