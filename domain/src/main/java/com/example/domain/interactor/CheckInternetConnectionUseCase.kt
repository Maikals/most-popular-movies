package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import javax.inject.Inject

class CheckInternetConnectionUseCase @Inject constructor(private val reachAbility: ReachAbility) :
        BaseCoRoutineUseCase<ReachAbilityEntity, EmptyParams>() {

    override suspend fun buildRepoCall(params: EmptyParams): ReachAbilityEntity = reachAbility.pingHealthBackOffice()

}