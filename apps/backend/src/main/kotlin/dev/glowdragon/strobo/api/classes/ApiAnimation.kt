package dev.glowdragon.strobo.api.classes

import dev.glowdragon.strobo.animation.Animation

class ApiAnimation(private val animation: Animation) {
  val id: String
    get() = animation.id

  val name: String
    get() = animation.name
}
