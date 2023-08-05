package dev.glowdragon.strobo.device

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "devices")
class DeviceEntity {
  @Id var id: String = ""

  var type: DeviceType = DeviceType.StripWS2812

  var ledCount = 1

  var registered: Boolean = false

  var name: String = ""

  var sortOrder: Int = 0

  var position: Int = 0

  var reverseMapping: Boolean = false
}
