package dev.glowdragon.strobo.animation

import kotlin.math.abs
import kotlin.random.Random

class RandomShotAnimation : Animation("random-shot", "Random Shot") {
  var pointPosition: Double = 0.0
  var speed = 3.0
  var timer = 0.0

  // TODO: Fortschritt vom Tick abhängig machen
  override fun updateProgress(args: UpdateProgressArgs) {
    pointPosition = (pointPosition + speed) % 57
    timer += Random.nextDouble() * 0.1

    if (timer > 3) {
      speed = 2.0
    }

    if (timer > 6) {
      speed = 1.0
    }

    if (timer > 8) {
      speed = 0.6
    }

    if (timer > 9) {
      speed = 0.2
    }

    if (timer > 10) {
      speed = 0.0
    }

    for (x in args.leds.colors.indices) {
      if (abs(pointPosition - x) <= 1) {
        if (speed > 0) {
          args.leds.colors[x].apply(
              (args.leds.colors[x].r),
              (args.leds.colors[x].g),
              (args.leds.colors[x].b),
              (args.leds.colors[x].w))
        } else {
          args.leds.colors[x].apply(255, 0, 0, 0)
        }
      } else {
        args.leds.colors[x].apply(0, 0, 0)
      }
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return RandomShotAnimation()
  }
}
