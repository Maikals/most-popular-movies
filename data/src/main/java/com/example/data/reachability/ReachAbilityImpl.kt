package com.example.data.reachability

import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbility
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class ReachAbilityImpl : ReachAbility {

    private val TAG = "ReachAbilityImpl"

    override fun checkHost(host: ReachAbilityCallParams): ReachAbilityEntity {

        val resultArray: ArrayList<InternetAddress> = arrayListOf()

        host.host.forEach { it ->
            val internetAddress = InternetAddress(it.host, it.port)

            var retries = 5
            var isReachAble = isConnected(internetAddress)

            while (!isReachAble && retries > 0) {
                Thread.sleep(2000)
                isReachAble = isConnected(internetAddress)
                retries--
            }

            internetAddress.isReachAble = isReachAble

            resultArray.add(internetAddress)
        }

        return ReachAbilityEntity(resultArray, resultArray.size > 0)

    }


    fun isConnected(internetAddress: InternetAddress): Boolean {
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