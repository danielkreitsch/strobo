package dev.glowdragon.strobo.animation

class ChristmasAnimation : Animation("xmas", "Weihnachten") {
  var speed = 0.01

  private var position = 0.0

  override fun updateProgress(args: UpdateProgressArgs) {
    position = (args.tick * speed) % 1

    for (i in args.leds.colors.indices) {
      if (((i + (args.leds.colors.size * position).toInt()) / 20) % 2 == 0) {
        args.leds.colors[i].apply(255, 0, 0)
      } else {
        args.leds.colors[i].apply(0, 255, 0)
      }
    }
  }

  override fun newInstance(parameters: Map<String, Any>): Animation {
    return ChristmasAnimation()
  }
}
