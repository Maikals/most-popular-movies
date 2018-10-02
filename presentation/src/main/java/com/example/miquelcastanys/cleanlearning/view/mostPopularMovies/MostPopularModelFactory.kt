package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.domain.interactor.GetSavedMoviesUseCase
import javax.inject.Inject


class MostPopularModelFactory @Inject constructor(private val mostPopularMoviesUseCaseCoRoutines: GetMostPopularMoviesUseCaseCoRoutines,
                                                  private val savedMoviesUseCase: GetSavedMoviesUseCase)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MostPopularMoviesViewModel(mostPopularMoviesUseCaseCoRoutines, savedMoviesUseCase) as T
    }
}