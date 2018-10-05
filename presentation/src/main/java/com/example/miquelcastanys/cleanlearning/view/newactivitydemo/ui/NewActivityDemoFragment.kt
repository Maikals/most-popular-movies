package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesNewModule
import com.example.miquelcastanys.cleanlearning.koinjector.newFragmentModule
import com.example.miquelcastanys.cleanlearning.observe
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import kotlinx.android.synthetic.main.new_activity_demo_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.get
import org.koin.standalone.getKoin
import org.koin.standalone.inject


class NewActivityDemoFragment : BaseFragment() {

    override fun setupFragmentComponent() {
        MostPopularMoviesApplication
                .applicationComponent
                .plus(BaseFragmentModule(context!!), MostPopularMoviesNewModule(this))
                .inject(this)
    }


    //val factory: MostPopularNewModelFactory by inject()

    companion object {
        const val TAG = "NewActivityDemoFragment"
        fun newInstance() = NewActivityDemoFragment()
    }

    lateinit var session: Scope
    val newActivityDemoViewModel: NewActivityDemoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.new_activity_demo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //loadKoinModules(newFragmentModule)
        session = getKoin().createScope("org.scope")
        createViewModel()
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

    override fun createViewModel() {
        // newActivityDemoViewModel = buildViewModel(factory, NewActivityDemoViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        session.close()
    }
}
