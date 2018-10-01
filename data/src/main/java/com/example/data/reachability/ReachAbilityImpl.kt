package com.example.data.reachability

import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import java.io.IOException
import java.net.*

class ReachAbilityImpl : ReachAbility {

    private val TAG = "ReachAbility"

    override fun checkBackEndHost(params: ReachAbilityCallParams): ReachAbilityEntity {
        //move to useCASE with a delay
        Thread.sleep(1000)
        params.internetAddress.isReachAble = isConnected(params.internetAddress)

        return ReachAbilityEntity(params.internetAddress)

    }

    //TODO REPLACE SOCKET WITH ADAPTER TO PERFORM PROPER HEALTH API CALL
    private fun isConnected(internetAddress: InternetAddress): Boolean {
        val socket = Socket()
        var isConnected: Boolean
        try {

            socket.connect(InetSocketAddress(internetAddress.host, internetAddress.port), 500)
            isConnected = true
            println("$TAG: Ping to host: ${internetAddress.host}")
        } catch (e: IOException) {
            isConnected = false
            println("$TAG: Error during ping to ${internetAddress.host}:" + e.printStackTrace())
        } finally {
            try {
                socket.close()
            } catch (e: IOException) {
                println(TAG + "Could not close the socket: " + e.printStackTrace())
            }
        }

        return isConnected

    }


}