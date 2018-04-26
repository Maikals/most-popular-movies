package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.interactor.UseCase
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import javax.inject.Inject

@PerFragment
class Presenter @Inject constructor(private val useCase: UseCase) {
}