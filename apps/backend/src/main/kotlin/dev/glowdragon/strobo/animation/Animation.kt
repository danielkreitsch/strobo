package dev.glowdragon.strobo.animation

import dev.glowdragon.strobo.device.LedArray

abstract class Animation(open val id: String, open val name: String) {
  abstract fun updateProgress(args: UpdateProgressArgs)

  abstract fun newInstance(parameters: Map<String, Any>): Animation

  data class UpdateProgressArgs(
      val leds: LedArray,
      val totalLedCount: Int,
      val position: Int,
      val beatsPerTick: Double,
      val tick: Int,
      val parameters: Map<String, Any>
  )
}
