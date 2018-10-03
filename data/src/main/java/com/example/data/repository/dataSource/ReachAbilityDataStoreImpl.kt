package com.example.data.repository.dataSource

import com.example.data.reachability.ReachAbilityService
import com.example.domain.entity.ReachAbilityEntity
import org.json.JSONObject
import javax.inject.Inject

class ReachAbilityDataStoreImpl @Inject constructor(private val reachAbilityService: ReachAbilityService) : ReachAbilityDataStore {
    override suspend fun pingHealthBackOffice(): ReachAbilityEntity {
        val jsonElement = reachAbilityService.pingHealthBackOffice().await()
        return ReachAbilityEntity(JSONObject(jsonElement.toString()).optBoolean("success"))
    }
}