package dev.glowdragon.strobo.device

data class Led(val color: Color) {
  fun setRgbColor(r: Int, g: Int, b: Int, w: Int = 0) {
    color.r = r
    color.g = g
    color.b = b
    color.w = w
  }

  fun setHsvColor(hue: Double, saturation: Double, value: Double) {
    color.applyHsv(hue, saturation, value)
  }
}
