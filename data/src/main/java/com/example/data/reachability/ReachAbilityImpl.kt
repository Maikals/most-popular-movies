package com.example.data.reachability

import com.example.data.repository.dataSource.ReachAbilityDataStore
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import kotlinx.coroutines.experimental.delay

class ReachAbilityImpl(private val reachAbilityDataStore: ReachAbilityDataStore) : ReachAbility {

    override suspend fun pingHealthBackOffice(): ReachAbilityEntity {
        delay(1000)//This delay is necessary due to DNS assignation when connecting to a router.DO NOT REMOVE
        return reachAbilityDataStore.pingHealthBackOffice()
    }

}