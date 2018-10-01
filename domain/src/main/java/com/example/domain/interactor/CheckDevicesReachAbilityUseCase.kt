package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.ReachAbilityDeviceCallParams
import com.example.domain.entity.ReachAbilityDeviceEntity
import com.example.domain.repository.ReachAbilityDevices
import kotlinx.coroutines.experimental.delay

class CheckDevicesReachAbilityUseCase constructor(private val reachAbilityDevices: ReachAbilityDevices) :
        BaseCoRoutineUseCase<ReachAbilityDeviceEntity, ReachAbilityDeviceCallParams>() {
    override suspend fun buildRepoCall(params: ReachAbilityDeviceCallParams): ReachAbilityDeviceEntity {
        delay(2000)
        return reachAbilityDevices.checkDevices(params)
    }
}