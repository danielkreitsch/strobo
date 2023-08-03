package com.danielkreitsch.strobo.backend.animation

import kotlin.math.roundToInt

class ColorSwitchAnimation : Animation("color-switch", "Farbwechsel 3")
{
    var speed = 1

    private val steps = 10

    // For efficiency
    private var progress = 0.0
    private var colorShift = 0.0

    override fun updateProgress(args: UpdateProgressArgs)
    {
        progress = args.tick * speed * args.beatsPerTick / steps

        colorShift = ((progress * steps).roundToInt().toDouble() / steps) % 1

        for (led in args.leds)
        {
            led.setHsvColor(colorShift, 1.0, 1.0)
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation
    {
        return ColorSwitchAnimation()
    }
}
