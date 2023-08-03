package com.danielkreitsch.strobo.backend.device

import com.danielkreitsch.strobo.backend.device.Color

data class Keyframe(
    val time: Int,
    val colorArray: Array<Color>
)
{
    val byteSize: Int
        get() = Int.SIZE_BYTES + (4 * Int.SIZE_BYTES) * this.colorArray.size
}
