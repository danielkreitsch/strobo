package dev.glowdragon.strobo.animation

import dev.glowdragon.strobo.device.Color
import kotlin.math.abs

class MovingPointsAnimation : Animation("moving-points", "Moving Points") {
  var speed = 2f

  override fun updateProgress(args: UpdateProgressArgs) {
    val originalColors =
        args.leds.colors.map { color -> Color(color.r, color.g, color.b, color.w) }.toTypedArray()

    for (color in args.leds.colors) {
      color.apply(color.r / 4, color.g / 4, color.b / 4, color.w / 4)
      // color.apply(0, 0, 0)
    }

    this.drawPoint(args.tick, args.leds.colors, originalColors, args.position)
    this.drawPoint(args.tick, args.leds.colors, originalColors, args.position, 100)
    this.drawPoint(args.tick, args.leds.colors, originalColors, args.position, 200)
    this.drawPoint(args.tick, args.leds.colors, originalColors, args.position, 300)
  }

  private fun drawPoint(
      tick: Int,
      colors: Array<Color>,
      originalColors: Array<Color>,
      devicePosition: Int,
      offset: Int = 0
  ) {
    val pointPosition = ((tick * speed + offset) % 600) - devicePosition // 173

    for (ledIndex in colors.indices) {
      var alpha = 0.0

      val distanceToPoint = abs(ledIndex - pointPosition)
      if (distanceToPoint < 3) {
        alpha = 1 - (distanceToPoint.toDouble() / 3)
      }

      if (alpha > 0) {
        colors[ledIndex].apply(
            (originalColors[ledIndex].r * alpha).toInt(),
            (originalColors[ledIndex].g * alpha).toInt(),
            (originalColors[ledIndex].b * alpha).toInt(),
            (originalColors[ledIndex].w * alpha).toInt())
      }
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return MovingPointsAnimation()
  }
}
