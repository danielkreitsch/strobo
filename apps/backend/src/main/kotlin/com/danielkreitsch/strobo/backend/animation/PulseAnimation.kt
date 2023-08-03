package com.danielkreitsch.strobo.backend.animation

import com.danielkreitsch.strobo.backend.device.Color
import kotlin.math.sin

class PulseAnimation : Animation("pulse", "Pulsieren")
{
    var speed = 0.01

    private var alpha: Double = 0.0

    override fun updateProgress(args: UpdateProgressArgs)
    {
        alpha = 0.5 * (sin(speed * Math.PI *  args.tick - 0.5 * Math.PI) + 1)

        for (i in  args.leds.colors.indices)
        {
            args.leds.colors[i].apply(
                (args.leds.colors[i].r * alpha).toInt(),
                (args.leds.colors[i].g * alpha).toInt(),
                (args.leds.colors[i].b * alpha).toInt(),
                (args.leds.colors[i].w * alpha).toInt())
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation
    {
        return PulseAnimation()
    }
}
