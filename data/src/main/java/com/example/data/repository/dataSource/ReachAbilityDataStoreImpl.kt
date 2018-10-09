package com.example.data.repository.dataSource

import com.example.data.reachability.ReachAbilityServiceAdapter
import com.example.domain.entity.ReachAbilityEntity
import org.json.JSONObject

class ReachAbilityDataStoreImpl (private val reachAbilityServiceAdapter: ReachAbilityServiceAdapter) : ReachAbilityDataStore {
    override suspend fun pingHealthBackOffice(): ReachAbilityEntity {
        val jsonElement = reachAbilityServiceAdapter.createReachAbilityService().pingHealthBackOffice().await()
        return ReachAbilityEntity(JSONObject(jsonElement.toString()).optBoolean("success"))
    }
}