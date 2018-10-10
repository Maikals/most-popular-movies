package com.example.miquelcastanys.cleanlearning.view.newactivitydemo

import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSavedMoviesUseCase
import com.example.miquelcastanys.cleanlearning.view.base.BaseUseCaseWrapper

class NewActivityUseCaseWrapper(var getMostPopularMoviesUseCase: GetMostPopularMoviesUseCase, var getSavedMoviesUseCase: GetSavedMoviesUseCase)
    : BaseUseCaseWrapper() {

    init {
        addUseCases(getMostPopularMoviesUseCase, getSavedMoviesUseCase)
    }
}