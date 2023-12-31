package dev.glowdragon.strobo.animation

import dev.glowdragon.strobo.util.apply

class StroboAnimation : Animation("strobo", "S T R O B O") {
  var lightsOn: Boolean = false

  override fun updateProgress(args: UpdateProgressArgs) {
    if (args.tick % 2 == 0) {
      lightsOn = !lightsOn
    }

    if (!lightsOn) {
      args.leds.colors.apply(0, 0, 0)
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return StroboAnimation()
  }
}
