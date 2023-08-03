package com.danielkreitsch.strobo.backend.device

data class Color(
    var r: Int,
    var g: Int,
    var b: Int,
    var w: Int = 0
)
{
    companion object
    {
        val Black
            get() = Color(0, 0, 0, 0)
        val White
            get() = Color(255, 255, 255, 255)
        val Red
            get() = Color(255, 0, 0, 0)
        val Green
            get() = Color(0, 255, 0, 0)
        val Blue
            get() = Color(0, 0, 255, 0)
        val Yellow
            get() = Color(255, 255, 0, 0)
    }

    fun apply(r: Int, g: Int, b: Int, w: Int = 0)
    {
        this.r = r
        this.g = g
        this.b = b
        this.w = w
    }

    fun apply(other: Color)
    {
        this.apply(other.r, other.g, other.b, other.w)
    }

    fun applyHsv(hue: Double, saturation: Double, value: Double)
    {
        val r: Double
        val g: Double
        val b: Double

        val h = (hue * 6).toInt()
        val f = (hue * 6 - h)
        val p = (value * (1 - saturation))
        val q = value * (1 - f * saturation)
        val t = value * (1 - (1 - f) * saturation)

        when
        {
            h == 0 ->
            {
                r = value
                g = t
                b = p
            }
            h == 1 ->
            {
                r = q
                g = value
                b = p
            }
            h == 2 ->
            {
                r = p
                g = value
                b = t
            }
            h == 3 ->
            {
                r = p
                g = q
                b = value
            }
            h == 4 ->
            {
                r = t
                g = p
                b = value
            }
            h <= 6 ->
            {
                r = value
                g = p
                b = q
            }
            else ->
            {
                throw RuntimeException("Something went wrong when converting from HSV to RGB. Input was $hue, $saturation, $value")
            }
        }

        this.apply((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
    }

    fun multiply(factor: Double): Color
    {
        this.r = (this.r * factor).toInt()
        this.g = (this.g * factor).toInt()
        this.b = (this.b * factor).toInt()
        this.w = (this.w * factor).toInt()
        return this
    }

    override fun toString(): String
    {
        return "($r,$g,$b,$w)"
    }
}

