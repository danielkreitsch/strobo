package dev.glowdragon.strobo.device

import dev.glowdragon.strobo.animation.*
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayList
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DeviceService(private val deviceRepository: DeviceRepository) {
  private final val logger = LoggerFactory.getLogger(this::class.java)

  final val allDevices: ArrayList<Device> = ArrayList()

  val registeredAndConnectedDevices: Array<Device>
    get() = allDevices.filter { device -> device.registered && device.connected }.toTypedArray()

  val lock = ReentrantLock()

  init {
    for (deviceEntity in this.deviceRepository.findAll()) {
      val device = Device(deviceEntity)
      this.allDevices.add(device)
    }
  }

  fun onDeviceConnect(id: String, type: DeviceType, ledCount: Int) {
    val device = this.getDevice(id)

    if (device != null) {
      if (device.connected) {
        this.onDeviceDisconnect(id)
      }

      device.connectedAt = System.currentTimeMillis()
      device.lastHealthSignalAt = System.currentTimeMillis()
      device.ledCount = ledCount

      this.deviceRepository.save(device.entity)

      this.logger.info(
          "Device $id is online (reversed: " +
              device.reverseMapping +
              ", position: " +
              device.position +
              ")")

      device.connected = true

      // Animation debugging
      if (device.id == "nadinedavid-buero") {
        Timer()
            .schedule(
                object : TimerTask() {
                  override fun run() {
                    device.animation = NadineDavidAnimation()
                    device.enabled = true
                  }
                },
                100)
        device.ledArray.setAll(0, 0, 0)
      }
    } else {
      this.addNewDevice(id, type, ledCount)
      this.logger.info("New device connected (Id: $id, Type: $type, LED Count: $ledCount)")
    }
  }

  fun onDeviceDisconnect(id: String) {
    val device = this.getDevice(id)

    if (device == null) {
      this.logger.error("Unexpected error: Unknown device disconnected")
      return
    }

    device.connected = false
    this.logger.info("Device $id is offline")
  }

  fun addNewDevice(id: String, type: DeviceType, ledCount: Int) {
    val deviceEntity = DeviceEntity()
    deviceEntity.id = id
    deviceEntity.type = type
    deviceEntity.ledCount = ledCount
    deviceEntity.name = "Neues Gerät"

    val device = Device(deviceEntity)
    device.connected = true
    device.connectedAt = System.currentTimeMillis()
    device.lastHealthSignalAt = System.currentTimeMillis()
    this.allDevices.add(device)

    this.deviceRepository.save(deviceEntity)
  }

  fun getDevice(id: String?): Device? {
    if (id == null) {
      return null
    }

    return allDevices.firstOrNull { device -> device.entity.id == id }
  }
}
