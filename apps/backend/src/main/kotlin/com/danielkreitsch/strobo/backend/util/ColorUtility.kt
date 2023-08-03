package com.danielkreitsch.strobo.backend.util

import com.danielkreitsch.strobo.backend.device.Color

/**
 * Creates an array and fills it with the given color.
 * Every instance has a unique reference.
 */
fun arrayOfColors(length: Int, color: Color): Array<Color>
{
    val array: Array<Color> = Array(length) { Color.Black }
    for (i in array.indices)
    {
        array[i] = Color(color.r, color.g, color.b, color.w)
    }
    return array
}

fun Array<Color>.apply(r: Int, g: Int, b: Int, w: Int = 0)
{
    for (color in this)
    {
        color.apply(r, g, b, w)
    }
}
