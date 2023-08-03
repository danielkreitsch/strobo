package com.danielkreitsch.strobo.backend.animation

import com.danielkreitsch.strobo.backend.device.Color
import com.danielkreitsch.strobo.backend.device.LedArray
import kotlin.math.abs

class CenterPulseAnimation(
    override val id: String,
    override val name: String,
    val rainbow: Boolean
) : Animation(id, name)
{
    override fun updateProgress(args: UpdateProgressArgs)
    {
        args.leds.setAll(0, 0, 0)

        val progress = (args.tick * args.beatsPerTick) % 1

        this.drawPoint(args.leds, progress, args.totalLedCount / 2, 1, args.totalLedCount / 2 + 10, 10.0)
        this.drawPoint(args.leds, progress, args.totalLedCount / 2, -1, args.totalLedCount / 2 + 10, 10.0)
    }

    private fun drawPoint(leds: LedArray, progress: Double, origin: Int, direction: Int, length: Int, pointWidth: Double)
    {
        val pointPosition = origin + (direction * length) * progress

        //println("" + progress + ", " + pointPosition)

        val hue = progress

        var ledIndex = 0
        for (led in leds)
        {
            var alpha = 0.0

            val distanceToPoint = abs(ledIndex - pointPosition)
            if (distanceToPoint < pointWidth)
            {
                alpha = 1 - (distanceToPoint / pointWidth)
            }

            if (alpha > 0)
            {
                //if (this.rainbow)
                //{
                leds.setHsvColor(ledIndex, hue, 1.0, alpha)
                //}
            }

            ledIndex++
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation
    {
        return CenterPulseAnimation(this.id, this.name, this.rainbow)//parameters["rainbow"]!! as Boolean)
    }
}
