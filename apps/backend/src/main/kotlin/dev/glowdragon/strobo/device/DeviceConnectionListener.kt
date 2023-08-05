package dev.glowdragon.strobo.device

import com.google.gson.GsonBuilder
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["device-connecting"])
class DeviceConnectionListener(private val deviceService: DeviceService) {
  @RabbitHandler
  fun onConnect(body: String, idk: ByteArray) {
    val connectingDevice = GsonBuilder().create().fromJson(body, ConnectingDevice::class.java)
    this.deviceService.onDeviceConnect(
        connectingDevice.id, DeviceType.values()[connectingDevice.type], connectingDevice.ledCount)
  }

  class ConnectingDevice {
    var id: String = ""
    var type: Int = 0
    var ledCount: Int = 0
  }
}
