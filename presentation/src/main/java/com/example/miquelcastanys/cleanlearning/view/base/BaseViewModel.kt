package com.example.miquelcastanys.cleanlearning.view.base

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.data.exeptions.ExceptionManager
import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.BaseEntity
import com.example.domain.entity.BaseParams
import com.example.domain.exceptions.CustomException

abstract class BaseViewModel : ViewModel() {

    fun <T : BaseEntity, Params : BaseParams> execute(useCase: BaseCoRoutineUseCase<T, Params>, params: Params, onResultOk: (T) -> Unit, onResultError: (String) -> Unit) {
        useCase.execute(params, {
            if (it.result) onResultOk(it)
            else onResultError(ExceptionManager.manageError(CustomException()))
        }, {
            Log.e("BaseViewModel", "Error", it)
            onResultError(ExceptionManager.manageError(it ?: CustomException()))
        })
    }
}