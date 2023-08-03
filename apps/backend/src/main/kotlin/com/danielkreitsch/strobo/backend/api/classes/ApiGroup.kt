package com.danielkreitsch.strobo.backend.api.classes

import com.danielkreitsch.strobo.backend.animation.Animation
import com.danielkreitsch.strobo.backend.device.Color
import com.danielkreitsch.strobo.backend.group.Group

class ApiGroup(
    private val group: Group
)
{
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
