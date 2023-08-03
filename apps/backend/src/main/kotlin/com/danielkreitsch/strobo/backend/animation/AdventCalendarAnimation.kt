package com.danielkreitsch.strobo.backend.animation

class AdventCalendarAnimation : Animation("advent-calendar", "Adventskalender") {
    override fun updateProgress(args: UpdateProgressArgs) {
        args.leds.forEachIndexed { index, led ->
            var brightness = 0.5f

            if (index % 2 == 0) {
                led.setRgbColor((255 * brightness).toInt(), 0, 0)
            } else {
                led.setRgbColor(0, (255 * brightness).toInt(), 0)
            }
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation {
        return AdventCalendarAnimation()
    }
}
