package dev.glowdragon.strobo.api.classes

import dev.glowdragon.strobo.animation.Animation
import dev.glowdragon.strobo.device.Color
import dev.glowdragon.strobo.device.Device
import dev.glowdragon.strobo.device.DeviceType

class ApiDevice(private val device: Device) {
  val id: String
    get() = device.entity.id

  val type: DeviceType
    get() = device.entity.type

  val name: String
    get() = device.entity.name

  val connected: Boolean
    get() = device.connected

  val registered: Boolean
    get() = device.registered

  val sortOrder: Int
    get() = device.sortOrder

  val active: Boolean
    get() = device.enabled

  val color: Color
    get() = device.color

  val animation: Animation?
    get() = device.animation

  val timeAdjustment: Int
    get() = device.timeAdjustment
}
