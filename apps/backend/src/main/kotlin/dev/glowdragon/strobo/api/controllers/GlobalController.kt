package dev.glowdragon.strobo.api.controllers

import dev.glowdragon.strobo.GlobalLightSettings
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class GlobalController(private val globalLightSettings: dev.glowdragon.strobo.GlobalLightSettings) {
  @PostMapping("/bpm")
  fun setBpm(@RequestBody params: SetBpmParams) {
    this.globalLightSettings.beatsPerTick = params.bpm / 1800
    println("BPM set to " + params.bpm + ", BPS set to " + this.globalLightSettings.beatsPerTick)
  }

  data class SetBpmParams(val bpm: Double)
}
