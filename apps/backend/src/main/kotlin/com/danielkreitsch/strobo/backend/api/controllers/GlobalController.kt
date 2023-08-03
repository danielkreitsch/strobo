package com.danielkreitsch.strobo.backend.api.controllers

import com.danielkreitsch.strobo.backend.GlobalLightSettings
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class GlobalController(
    private val globalLightSettings: com.danielkreitsch.strobo.backend.GlobalLightSettings
)
{
    @PostMapping("/bpm")
    fun setBpm(@RequestBody params: SetBpmParams)
    {
        this.globalLightSettings.beatsPerTick = params.bpm / 1800
        println("BPM set to " + params.bpm + ", BPS set to " + this.globalLightSettings.beatsPerTick)
    }

    data class SetBpmParams(
        val bpm: Double
    )
}
