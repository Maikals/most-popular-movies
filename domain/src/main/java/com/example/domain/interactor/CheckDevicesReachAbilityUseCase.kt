package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.InternetAddressParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbilityDevices
import kotlinx.coroutines.delay

class CheckDevicesReachAbilityUseCase(private val reachAbilityDevices: ReachAbilityDevices) :
        BaseCoRoutineUseCase<ReachAbilityEntity, InternetAddressParams>() {
    override suspend fun buildRepoCall(params: InternetAddressParams): ReachAbilityEntity {
        delay(2000)
        return reachAbilityDevices.checkDevices(params)
    }
}