package com.danielkreitsch.strobo.backend

import com.danielkreitsch.strobo.backend.device.Color
import org.springframework.stereotype.Component

@Component
class GlobalLightSettings
{
    var brightness: Double = 1.0

    var beatsPerTick: Double = 76.0 / 1800 // Hypa Hypa

    val beatsPerMinute: Double
        get() = beatsPerTick * 1800
}
