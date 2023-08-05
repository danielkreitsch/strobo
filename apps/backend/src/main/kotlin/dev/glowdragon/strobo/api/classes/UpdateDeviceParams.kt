package dev.glowdragon.strobo.api.classes

import dev.glowdragon.strobo.device.Color

data class UpdateDeviceParams(
    val name: String?,
    val active: Boolean?,
    val brightness: Double?,
    val color: Color?,
    val animationId: String?,
    val timeAdjustment: Int?,
    val attributes: HashMap<String, Any>?
)
