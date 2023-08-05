package dev.glowdragon.strobo.device

import java.nio.ByteBuffer
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DeviceMessageSender(private val rabbitTemplate: RabbitTemplate) {
  private final val logger = LoggerFactory.getLogger(this::class.java)

  fun sendTimedColorArray(device: Device, time: Long, colorArray: Array<Color>) {
    if (!device.connected) {
      this.logger.warn("Failed sending a color packet due to the device not being connected.")
      return
    }

    if (device.connectedAt == null) {
      this.logger.warn(
          "Failed sending a color packet due to the device connection time not being set.")
      return
    }

    val buffer = ByteBuffer.allocate(Int.SIZE_BYTES + (4 * Byte.SIZE_BYTES) * colorArray.size)

    val calibratedTime: Int = (time - (device.connectedAt!! + device.timeAdjustment)).toInt()
    buffer.putInt(calibratedTime)

    for (ledIndex in colorArray.indices) {
      buffer.put(colorArray[ledIndex].r.toByte())
      buffer.put(colorArray[ledIndex].g.toByte())
      buffer.put(colorArray[ledIndex].b.toByte())
      buffer.put(colorArray[ledIndex].w.toByte())
    }

    this.sendMessage(device.id, buffer.array())
  }

  fun sendDisconnectedNotification(deviceId: String) {
    this.sendMessage(deviceId, ByteArray(1))
  }

  private fun sendMessage(deviceId: String, message: ByteArray) {
    this.rabbitTemplate.convertAndSend("amq.topic", deviceId, message)
  }
}
