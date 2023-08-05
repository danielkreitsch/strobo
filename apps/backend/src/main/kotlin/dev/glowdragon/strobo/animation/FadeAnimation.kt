package dev.glowdragon.strobo.animation

class FadeAnimation : Animation("fade", "Farbwechsel 1") {
  var speed = 0.001

  private var colorShift = 0.0

  override fun updateProgress(args: UpdateProgressArgs) {
    colorShift = (args.tick * speed) % 1

    for (i in args.leds.colors.indices) {
      args.leds.colors[i].applyHsv(colorShift, 1.0, 1.0)
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return FadeAnimation()
  }
}
