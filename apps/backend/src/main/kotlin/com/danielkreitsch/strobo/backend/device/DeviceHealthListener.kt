package com.danielkreitsch.strobo.backend.device

import com.google.gson.GsonBuilder
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RabbitListener(queues = ["device-health"])
class DeviceHealthListener(
    private val deviceService: DeviceService,
    private val deviceMessageSender: DeviceMessageSender
)
{
    val lastDeviceReconnection = HashMap<String, Long>()

    @RabbitHandler
    fun receive(body: String, idk: ByteArray)
    {
        val deviceHealthPing = GsonBuilder().create().fromJson(body, DeviceHealthPing::class.java)
        val deviceId = deviceHealthPing.deviceId

        val device = this.deviceService.getDevice(deviceId)
        if (device != null)
        {
            device.lastHealthSignalAt = System.currentTimeMillis()
        }

        val disconnected = device == null || !device.connected
        val reconnectionOnCooldown = this.lastDeviceReconnection.contains(deviceId) && this.lastDeviceReconnection[deviceId]!! > System.currentTimeMillis() - 1000 * 10
        if (disconnected && !reconnectionOnCooldown)
        {
            this.deviceMessageSender.sendDisconnectedNotification(deviceId)
            this.lastDeviceReconnection[deviceId] = System.currentTimeMillis()
        }
    }

    class DeviceHealthPing
    {
        var deviceId: String = ""
    }
}
