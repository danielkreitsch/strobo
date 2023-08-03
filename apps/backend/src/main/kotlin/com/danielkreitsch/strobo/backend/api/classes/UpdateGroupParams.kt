package com.danielkreitsch.strobo.backend.api.classes

import com.danielkreitsch.strobo.backend.device.Color

data class UpdateGroupParams(
    val name: String?,
    val deviceIds: Array<String>?,
    val active: Boolean?,
    val brightness: Double?,
    val color: Color?,
    val animationId: String?
)
