package dev.glowdragon.strobo.animation

class RainbowAnimation : Animation("rainbow", "Rainbow") {
  override fun updateProgress(args: UpdateProgressArgs) {
    val position = args.tick - args.position

    for (x in args.leds.colors.indices) {
      val hue: Double = Math.floorMod(x - position, 173).toDouble() / 173
      args.leds.colors[x].applyHsv(hue, 1.0, 1.0)
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return RainbowAnimation()
  }
}
