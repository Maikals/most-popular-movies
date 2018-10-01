package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import kotlinx.coroutines.experimental.delay

class CheckInternetConnectionUseCase constructor(private val reachAbility: ReachAbility) :
        BaseCoRoutineUseCase<ReachAbilityEntity, ReachAbilityCallParams>() {
    override suspend fun buildRepoCall(params: ReachAbilityCallParams): ReachAbilityEntity {
        delay(1000)
        return reachAbility.checkBackEndHost(params)
    }
}