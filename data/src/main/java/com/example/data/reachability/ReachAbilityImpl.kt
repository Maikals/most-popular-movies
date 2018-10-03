package com.example.data.reachability

import com.example.data.repository.dataSource.ReachAbilityDataStore
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import kotlinx.coroutines.experimental.delay
import javax.inject.Inject

class ReachAbilityImpl @Inject constructor(private val reachAbilityService: ReachAbilityDataStore) : ReachAbility {

    override suspend fun pingHealthBackOffice(): ReachAbilityEntity {
        delay(500)//This delay is necessary due to DNS assignation when connecting to a router.DO NOT REMOVE
        return reachAbilityService.pingHealthBackOffice()
    }


}