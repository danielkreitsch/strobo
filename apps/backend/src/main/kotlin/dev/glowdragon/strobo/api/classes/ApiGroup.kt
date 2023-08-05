package dev.glowdragon.strobo.api.classes

import dev.glowdragon.strobo.animation.Animation
import dev.glowdragon.strobo.device.Color
import dev.glowdragon.strobo.group.Group

class ApiGroup(private val group: Group) {
  val id: String
    get() = group.id

  val name: String
    get() = group.name

  val sortOrder: Int
    get() = group.sortOrder

  val devices: ArrayList<ApiDevice>
    get() = ApiDeviceList(group.devices).apiDevices

  val active: Boolean
    get() = group.active

  val color: Color
    get() = group.color

  val animation: Animation?
    get() = group.animation
}
