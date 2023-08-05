package dev.glowdragon.strobo

import dev.glowdragon.strobo.animation.Animation
import dev.glowdragon.strobo.animation.AnimationService
import dev.glowdragon.strobo.device.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Master(
    private val globalLightSettings: dev.glowdragon.strobo.GlobalLightSettings,
    private val deviceService: DeviceService,
    private val animationService: AnimationService,
    private val deviceMessageSender: DeviceMessageSender
) {
  private final val logger = LoggerFactory.getLogger(this::class.java)

  var differentDevicePositions = false

  var lastUpdateTime: Double = 0.001 * System.currentTimeMillis()

  var updateColorsTimer = 0.0
  var checkHealthTimer = 0.0

  var updateColorsTick = 0

  init {
    GlobalScope.launch {
      while (true) {
        val deltaTime = (0.001 * System.currentTimeMillis()) - lastUpdateTime
        lastUpdateTime = 0.001 * System.currentTimeMillis()
        this@Master.update(deltaTime)
      }
    }
  }

  fun update(deltaTime: Double) {
    synchronized(this.deviceService.lock) {
      this.updateColorsTimer += deltaTime
      if (this.updateColorsTimer >= 0.0333333333) {
        this.updateColorsTimer = 0.0
        this.updateColors(deltaTime)
      }

      this.checkHealthTimer += deltaTime
      if (this.checkHealthTimer >= 1) {
        this.checkHealthTimer = 0.0
        this.checkHealth(deltaTime)
      }
    }
  }

  fun checkHealth(deltaTime: Double) {
    for (device in deviceService.allDevices) {
      val healthSignalRecentlyReceived =
          device.lastHealthSignalAt != null &&
              device.lastHealthSignalAt!! > System.currentTimeMillis() - 1500
      if (healthSignalRecentlyReceived && !device.connected) {
        this.logger.info("Device " + device.id + " sent a health signal but is offline")
      } else if (!healthSignalRecentlyReceived && device.connected) {
        this.deviceService.onDeviceDisconnect(device.id)
      }
    }
  }

  fun updateColors(deltaTime: Double) {
    val time = System.currentTimeMillis() + 500

    val devices = deviceService.registeredAndConnectedDevices
    this.sendDeviceStates(time, deltaTime, devices)

    this.updateColorsTick++
  }

  fun sendDeviceStates(time: Long, deltaTime: Double, devices: Array<Device>) {
    val totalLedCount = devices.sumOf { device -> device.ledCount }

    for (device in devices) {
      if (device.enabled) {
        device.ledArray.setAll(device.color)
      } else {
        device.ledArray.setAll(0, 0, 0)
      }

      if (device.enabled && device.animation != null) {
        val parameters = HashMap<String, Any>()

        // parameters["animationPoints"] = device.animationPoints

        if (this.differentDevicePositions) {
          device.animation!!.updateProgress(
              Animation.UpdateProgressArgs(
                  device.ledArray,
                  totalLedCount,
                  device.position,
                  this.globalLightSettings.beatsPerTick,
                  this.updateColorsTick,
                  parameters))
        } else {
          device.animation!!.updateProgress(
              Animation.UpdateProgressArgs(
                  device.ledArray,
                  device.ledCount,
                  0,
                  this.globalLightSettings.beatsPerTick,
                  this.updateColorsTick,
                  parameters))
        }
      }

      if (!device.ledArray.contentEquals(device.previousLedArray) ||
          (!device.enabled && device.numSequentialUpdatesWithoutColorChange < 100)) {
        device.previousLedArray.copyFrom(device.ledArray)

        if (device.reverseMapping) {
          device.ledArray.reverse()
        }

        this.deviceMessageSender.sendTimedColorArray(device, time, device.ledArray.colors)
        // println("Sent " + device.ledArray.colors[0].r + "," + device.ledArray.colors[0].g + "," +
        // device.ledArray.colors[0].b + " to " + device.id)

        if (device.previousLedArray.contentEquals(device.ledArray)) {
          device.numSequentialUpdatesWithoutColorChange++
        } else {
          device.numSequentialUpdatesWithoutColorChange = 0
        }
      }
    }
  }
}
