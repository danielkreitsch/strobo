package dev.glowdragon.strobo.group

import dev.glowdragon.strobo.animation.Animation
import dev.glowdragon.strobo.device.Color
import dev.glowdragon.strobo.device.Device

class Group(val entity: GroupEntity) {
  val id: String
    get() = this.entity.id

  var name: String
    get() = this.entity.name
    set(value) {
      this.entity.name = value
    }

  var sortOrder: Int
    get() = this.entity.sortOrder
    set(value) {
      this.entity.sortOrder = value
    }

  var devices: ArrayList<Device> = ArrayList()

  var active: Boolean = true

  var brightness: Double = 1.0

  var color: Color = Color.Blue.copy()

  var animation: Animation? = null
}
