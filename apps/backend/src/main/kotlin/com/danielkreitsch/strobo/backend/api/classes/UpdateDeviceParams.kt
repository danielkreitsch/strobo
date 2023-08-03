package com.danielkreitsch.strobo.backend.api.classes

import com.danielkreitsch.strobo.backend.animation.ShopItemAnimationPoint
import com.danielkreitsch.strobo.backend.device.Color

data class UpdateDeviceParams(
    val name: String?,
    val active: Boolean?,
    val brightness: Double?,
    val color: Color?,
    val animationId: String?,
    val timeAdjustment: Int?,
    val attributes: HashMap<String, Any>?
)
