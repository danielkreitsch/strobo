package com.danielkreitsch.strobo.backend.device

import com.danielkreitsch.strobo.backend.animation.Animation
import com.danielkreitsch.strobo.backend.animation.ShopItemAnimationPoint

class Device(
    val entity: DeviceEntity
)
{
    val id: String
        get() = this.entity.id

    val type: DeviceType
        get() = this.entity.type

    var ledCount: Int
        get() = this.entity.ledCount
        set(value)
        {
            this.entity.ledCount = value
        }

    var name: String
        get() = this.entity.name
        set(value)
        {
            this.entity.name = value
        }

    var registered: Boolean
        get() = this.entity.registered
        set(value)
        {
            this.entity.registered = value
        }

    var sortOrder: Int
        get() = this.entity.sortOrder
        set(value)
        {
            this.entity.sortOrder = value
        }

    var position: Int
        get() = this.entity.position
        set(value)
        {
            this.entity.position = value
        }

    var reverseMapping: Boolean
        get() = this.entity.reverseMapping
        set(value)
        {
            this.entity.reverseMapping = value
        }

    var connected: Boolean = false

    var enabled: Boolean = false

    var brightness: Double = 1.0

    var color: Color = Color.White.copy()

    var animation: Animation? = null

    var connectedAt: Long? = null

    var timeAdjustment: Int = 0

    var lastHealthSignalAt: Long? = null

    val ledArray: LedArray = LedArray(this.ledCount)

    val previousLedArray: LedArray = LedArray(this.ledCount)

    var numSequentialUpdatesWithoutColorChange: Int = 0

    val attributes: HashMap<String, Any> = HashMap()
}
