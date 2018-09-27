package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility

class CheckInternetConnectionUseCase constructor(private val reachAbility: ReachAbility) :
        BaseCoRoutineUseCase<ReachAbilityEntity, ReachAbilityCallParams>() {
    override fun buildRepoCall(params: ReachAbilityCallParams): ReachAbilityEntity = reachAbility.checkHost(params)
}