package com.danielkreitsch.strobo.backend.animation

import com.danielkreitsch.strobo.backend.device.Color
import com.danielkreitsch.strobo.backend.device.LedArray
import java.io.File

fun main() {
    NadineDavidAnimation().updateProgress(Animation.UpdateProgressArgs(LedArray(695), 0, 0, 120.0, 0, emptyMap()))
}

/**
 * Back:    0 - 181
 * Left:  182 - 339
 * Front: 340 - 534
 * Right: 535 - 694
 */
class NadineDavidAnimation : Animation("nadine-david", "Nadean & David") {
    enum class Wall(val size: Int, val index: (Int) -> Int) {
        BACK(182, { BACK.size - 1 - it }),
        LEFT(158, { BACK.size + it }),
        FRONT(195, { BACK.size + LEFT.size + it }),
        RIGHT(160, { TOTAL_LED_COUNT - 1 - it });

        companion object {
            const val TOTAL_LED_COUNT = 695
        }
    }

    companion object {
        const val BRIGHTNESS = 0.2
    }

    private var leds: Array<Color> = Array(Wall.TOTAL_LED_COUNT) { Color.Black }

    private fun clear() = leds.forEach { it.apply(Color.Black) }

    private fun setColor(index: Int, color: Color, brightness: Double = 1.0) =
        leds[index].apply((color.r * brightness).toInt(), (color.g * brightness).toInt(), (color.b * brightness).toInt())

    fun setWallColor(wall: Wall, index: Int, color: Color, brightness: Double = 1.0) = setColor(wall.index(index), color, brightness)

    fun multiplyBrightness(wall: Wall, index: Int, brightness: Double) = setColor(wall.index(index), leds[wall.index(index)], brightness)

    var saved = false

    override fun updateProgress(args: UpdateProgressArgs) {
        this.leds = args.leds.colors
        clear()
        (0 until Wall.LEFT.size).forEach { setWallColor(Wall.LEFT, it, Color(0, 255, 255), BRIGHTNESS) }
        (0 until Wall.RIGHT.size).forEach { setWallColor(Wall.RIGHT, it, Color(255, 0, 255), BRIGHTNESS) }
        setGradient(Wall.FRONT, Color(0, 255, 255), Color(255, 0, 255), BRIGHTNESS, 0.4f, 0.6f)
        setGradient(Wall.BACK, Color(0, 255, 255), Color(255, 0, 255), BRIGHTNESS, 0.4f, 0.6f)

        if (!saved) {
            val colorMap = mutableMapOf<Color, MutableList<Int>>()
            leds.forEachIndexed { index, color ->
                colorMap.getOrPut(color) { mutableListOf() }.add(index)
            }

            var script = StringBuilder()

            // Compress algorithm

            script.append("int ledIndices[${Wall.TOTAL_LED_COUNT}] = {")
            for (ledIndex in 0 until Wall.TOTAL_LED_COUNT) {
                val color = leds[ledIndex]
                for (i in 0 until colorMap.size) {
                    if (colorMap.keys.elementAt(i) == color) {
                        script.append(i)
                        script.append(", ")
                        break
                    }
                }
            }
            script.delete(script.length - 2, script.length)
            script.append("};\n")

            // R
            script.append("int ledR[${colorMap.size}] = {")
            colorMap.forEach { (color, _) ->
                script.append(color.r)
                script.append(", ")
            }
            script.delete(script.length - 2, script.length)
            script.append("};\n")

            // G
            script.append("int ledG[${colorMap.size}] = {")
            colorMap.forEach { (color, _) ->
                script.append(color.g)
                script.append(", ")
            }
            script.delete(script.length - 2, script.length)
            script.append("};\n")

            // B
            script.append("int ledB[${colorMap.size}] = {")
            colorMap.forEach { (color, _) ->
                script.append(color.b)
                script.append(", ")
            }
            script.delete(script.length - 2, script.length)
            script.append("};\n")

            File("arduino.ino").writeText(script.toString())
            saved = true
        }
    }

    private fun setGradient(wall: Wall, startColor: Color, endColor: Color, brightness: Double, gradientStartPercent: Float, gradientEndPercent: Float) {
        val diff = Color(endColor.r - startColor.r, endColor.g - startColor.g, endColor.b - startColor.b)

        val gradientStart = (wall.size * gradientStartPercent).toInt()
        val gradientEnd = (wall.size * gradientEndPercent).toInt()
        val gradientSize = gradientEnd - gradientStart

        for (i in 0 until wall.size) {
            val color = when {
                i < gradientStart -> Color(startColor.r, startColor.g, startColor.b)
                i > gradientEnd -> Color(startColor.r + diff.r, startColor.g + diff.g, startColor.b + diff.b)
                else -> {
                    val ratio = (i - gradientStart).toFloat() / gradientSize
                    Color(
                        (startColor.r + ratio * diff.r).toInt(),
                        (startColor.g + ratio * diff.g).toInt(),
                        (startColor.b + ratio * diff.b).toInt()
                    )
                }
            }
            setWallColor(wall, i, color, brightness)
        }
    }

    override fun newInstance(parameters: Map<String, Any>): Animation = NadineDavidAnimation()
}
