package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.newactivitydemo

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesNewModule
import com.example.miquelcastanys.cleanlearning.interfaces.MostPopularMoviesActivityFragmentInterface
import com.example.miquelcastanys.cleanlearning.observe
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivityFragmentInterface
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment
import com.example.miquelcastanys.cleanlearning.view.base.ReachAbilityManager
import kotlinx.android.synthetic.main.new_activity_demo_fragment.*
import javax.inject.Inject


class NewActivityDemoFragment : BaseFragment() {

    @Inject
    lateinit var reachAbilityManager: ReachAbilityManager

    private var mostPopularMoviesActivityFragmentInterface: BaseActivityFragmentInterface? = null


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
        createViewModel()
        button_request_movies_new_fragment.setOnClickListener {
            viewModel.getMovies()
            reachAbilityManager.startTimerVUE()
        }

        observe(viewModel.onDataReceived) {
            message.text = it.toString()
        }

        reachAbilityManager.stopTimerVUE()

        Toast.makeText(activity,"INTERNET: ${mostPopularMoviesActivityFragmentInterface?.isInternetReachable()}",Toast.LENGTH_LONG).show()

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mostPopularMoviesActivityFragmentInterface = context as? BaseActivityFragmentInterface
    }

    private fun createViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(NewActivityDemoViewModel::class.java)
    }
}
