package com.danielkreitsch.strobo.backend.animation

import com.danielkreitsch.strobo.backend.device.Color
import com.danielkreitsch.strobo.backend.util.apply
import kotlin.math.sin

class ShopItemAnimation : Animation("shop-item", "Dorfladen")
{
    var speed = 0.05

    override fun updateProgress(args: UpdateProgressArgs)
    {
        val animationPoints = args.parameters["animationPoints"] as Array<ShopItemAnimationPoint>

        args.leds.colors.apply(0, 0, 0) // This doesn't work yet: args.leds.setAll(0, 0, 0)

        for (point in animationPoints)
        {
            this.drawPoint(args.tick, args.leds.colors, point.position, point.width, point.color)
        }
    }

    private fun drawPoint(tick: Int, colors: Array<Color>, pointPosition: Int = 0, pointWidth: Int, color: Color)
    {
        val timeAlpha = 0.5 * (sin(speed * Math.PI * tick - 0.5 * Math.PI) + 1)

        for (ledIndex in colors.indices)
        {
            var positionAlpha = 0.0
            if (ledIndex >= pointPosition && ledIndex < pointPosition + pointWidth)
            {
                positionAlpha = 1.0
                //distanceAlpha = 1 - (distanceToPoint.toDouble() / pointRadius)
            }

            val alpha = positionAlpha * timeAlpha
            if (alpha > 0)
            {
                colors[ledIndex].apply(
                    (color.r * alpha).toInt(),
                    (color.g * alpha).toInt(),
                    (color.b * alpha).toInt(),
                    (color.w * alpha).toInt())
            }
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation
    {
        return ShopItemAnimation()
    }
}
