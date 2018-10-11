package com.example.data.reachability

import com.google.gson.JsonElement
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ReachAbilityService {
    @GET("status/health")
    fun pingHealthBackOffice(): Deferred<JsonElement>
}