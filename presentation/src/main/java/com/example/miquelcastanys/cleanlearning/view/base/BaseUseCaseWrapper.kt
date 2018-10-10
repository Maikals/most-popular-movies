package com.example.miquelcastanys.cleanlearning.view.base

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.base.Log
import com.example.domain.entity.BaseEntity
import com.example.domain.entity.BaseParams
import java.lang.Exception

abstract class BaseUseCaseWrapper {

    companion object {
        private const val TAG  = "BaseUseCaseWrapper"
    }

    val list: ArrayList<BaseCoRoutineUseCase<BaseEntity, BaseParams>> = arrayListOf()

    protected fun <T : BaseCoRoutineUseCase<*, *>> addUseCases(vararg useCase: T) =
            useCase.forEach { useCaseIterator ->
                try {
                    list.add(useCaseIterator as BaseCoRoutineUseCase<BaseEntity, BaseParams>)
                }catch (e:Exception){
                    Log.e(TAG,"UseCase does not extend from BaseCoRoutineUseCase<BaseEntity, BaseParams>, please check Usecases used")
                }
            }
}