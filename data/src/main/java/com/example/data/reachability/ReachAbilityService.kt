package com.example.data.reachability

import com.google.gson.JsonElement
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

interface ReachAbilityService {
    @GET("status/health")
    fun pingHealthBackOffice(): Deferred<JsonElement>
}