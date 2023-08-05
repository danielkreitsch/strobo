package dev.glowdragon.strobo.api.classes

import dev.glowdragon.strobo.device.Color

data class UpdateGroupParams(
    val name: String?,
    val deviceIds: Array<String>?,
    val active: Boolean?,
    val brightness: Double?,
    val color: Color?,
    val animationId: String?
)
