package com.danielkreitsch.strobo.backend.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import com.danielkreitsch.strobo.backend.device.Device

class ApiDeviceList(devices: Collection<Device>)
{
    @JsonProperty("devices")
    val apiDevices: ArrayList<ApiDevice> = ArrayList()

    init
    {
        for (device in devices)
        {
            apiDevices.add(ApiDevice(device))
        }
    }
}
