package dev.glowdragon.strobo.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import dev.glowdragon.strobo.device.Device

class ApiDeviceList(devices: Collection<Device>) {
  @JsonProperty("devices") val apiDevices: ArrayList<ApiDevice> = ArrayList()

  init {
    for (device in devices) {
      apiDevices.add(ApiDevice(device))
    }
  }
}
