package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSavedMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.view.base.BaseUseCaseWrapper

class MostPopularMoviesUseCaseWrapper(val useCaseAllMovies: GetMostPopularMoviesUseCase,
                                      val localUseCase: GetSavedMoviesUseCase,
                                      val getSearchMoviesUseCase: GetSearchMoviesUseCase) : BaseUseCaseWrapper() {
    init {
        addUseCases(useCaseAllMovies, localUseCase, getSearchMoviesUseCase)
    }
}


