package dev.glowdragon.strobo.animation

import kotlin.math.sin

class ColorPulseAnimation : Animation("color-pulse", "Farbwechsel 2") {
  var colorVarianceCount = 5
  var speed = 0.005

  private var alpha: Double = 1.0
  private var colorShift = 0.0

  override fun updateProgress(args: UpdateProgressArgs) {
    alpha = 0.5 * (sin(speed * Math.PI * args.tick - 0.5 * Math.PI) + 1)
    colorShift = (((speed * args.tick).toInt() / 2).toDouble() / colorVarianceCount) % 1

    for (i in args.leds.colors.indices) {
      args.leds.colors[i].applyHsv(colorShift, 1.0, alpha)
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return ColorPulseAnimation()
  }
}
