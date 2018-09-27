package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import javax.inject.Inject


class MostPopularModelFactory @Inject constructor(private val mostPopularMoviesUseCaseCoRoutines: GetMostPopularMoviesUseCaseCoRoutines)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MostPopularMoviesViewModel(mostPopularMoviesUseCaseCoRoutines) as T
    }
}