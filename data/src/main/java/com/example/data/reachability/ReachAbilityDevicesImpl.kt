package com.example.data.reachability

import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityDeviceCallParams
import com.example.domain.entity.ReachAbilityDeviceEntity
import com.example.domain.repository.ReachAbilityDevices
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ReachAbilityDevicesImpl : ReachAbilityDevices {

    override fun checkDevices(params: ReachAbilityDeviceCallParams): ReachAbilityDeviceEntity {

        val resultArray: ArrayList<InternetAddress> = arrayListOf()

        params.hosts.forEach { it ->
            val internetAddress = InternetAddress(it.host, it.port)
            checkDeviceReachAbleViaUDP(internetAddress)
            resultArray.add(internetAddress)
        }

        return ReachAbilityDeviceEntity(resultArray, resultArray.size > 0)

    }


    private val SEND_DATA = "lalala".toByteArray()

    private fun checkDeviceReachAbleViaUDP(internetAddress: InternetAddress) {

        var isReachAble = false
        val datagramSocket = DatagramSocket()

        try {

            datagramSocket.soTimeout = 5000

            val socketAddress = InetAddress.getByName(internetAddress.host)
            val sendPacket = DatagramPacket(SEND_DATA, SEND_DATA.size, socketAddress, internetAddress.port)
            datagramSocket.send(sendPacket)

            val buf = ByteArray(15000)

            val receivePacket = DatagramPacket(buf, buf.size)
            datagramSocket.receive(receivePacket)

            println("ReachAbilityDevices: Request packet sent to:  ${socketAddress.hostAddress} ;")

            isReachAble = !receivePacket.data.isEmpty()

            if (isReachAble) {
                println("ReachAbilityDevices: UDP ESTABLISHED: ${internetAddress.host}")
            }

        } catch (e: Exception) {
            isReachAble = false
            println("ReachAbilityDevices: UDP ERROR $e")
        } finally {
            datagramSocket.close()
            if (!isReachAble) {
                println("ReachAbilityDevices: UDP Message ip not responding: ${internetAddress.host} ")
            }
        }


        internetAddress.isReachAble = isReachAble

    }
}