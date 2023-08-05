package dev.glowdragon.strobo.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import dev.glowdragon.strobo.animation.Animation

class ApiAnimationList(animations: Array<Animation>) {
  @JsonProperty("animations") val apiAnimations: ArrayList<ApiAnimation> = ArrayList()

  init {
    for (animation in animations) {
      apiAnimations.add(ApiAnimation(animation))
    }
  }
}
