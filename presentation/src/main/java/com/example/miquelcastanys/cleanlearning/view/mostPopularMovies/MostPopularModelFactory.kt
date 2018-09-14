package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoroutines
import javax.inject.Inject


class MostPopularModelFactory @Inject constructor(private val mostPopularMoviesUseCaseCoroutines: GetMostPopularMoviesUseCaseCoroutines)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MostPopularMoviesViewModel(mostPopularMoviesUseCaseCoroutines) as T
    }
}