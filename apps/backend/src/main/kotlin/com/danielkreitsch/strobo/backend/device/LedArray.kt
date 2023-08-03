package com.danielkreitsch.strobo.backend.device

class LedArray(
    size: Int
) : Iterable<Led>
{
    val leds = Array(size) { Led(Color.Black) }

    val size: Int
        get() = this.leds.size

    val colors: Array<Color>
        get() = this.leds.map { led -> led.color }.toTypedArray()

    init
    {
        for (ledIndex in leds.indices)
        {
            leds[ledIndex] = Led(Color(0, 0, 0, 0))
        }
    }

    override fun iterator(): Iterator<Led>
    {
        return this.leds.iterator()
    }

    fun setAll(r: Int, g: Int, b: Int, w: Int = 0)
    {
        for (led in this.leds)
        {
            led.setRgbColor(r, g, b, w)
        }
    }

    fun setAll(color: Color)
    {
        setAll(color.r, color.g, color.b, color.w)
    }

    fun reverse()
    {
        this.leds.reverse()
    }

    fun contentEquals(otherLedArray: LedArray): Boolean
    {
        for (led in this)
        {
            for (otherLed in otherLedArray)
            {
                if (led.color.r != otherLed.color.r
                    || led.color.g != otherLed.color.g
                    || led.color.b != otherLed.color.b
                    || led.color.w != otherLed.color.w)
                {
                    return false
                }
            }
        }
        return true
    }

    fun copyFrom(otherLedArray: LedArray)
    {
        for (led in this)
        {
            for (otherLed in otherLedArray)
            {
                led.color.r = otherLed.color.r
                led.color.g = otherLed.color.g
                led.color.b = otherLed.color.b
                led.color.w = otherLed.color.w
            }
        }
    }

    fun setHsvColor(ledIndex: Int, hue: Double, saturation: Double, value: Double)
    {
        this.leds[ledIndex].setHsvColor(hue, saturation, value)
    }

    fun setRgbColor(ledIndex: Int, r: Int, g: Int, b: Int, w: Int)
    {
        this.leds[ledIndex].setRgbColor(r, g, b, w)
    }
}
