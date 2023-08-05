package dev.glowdragon.strobo.animation

import dev.glowdragon.strobo.configuration.AnimationServiceConfig
import org.springframework.stereotype.Service

@Service
class AnimationService(
    private val animationRepository: AnimationRepository,
    private val animationServiceConfig: AnimationServiceConfig
) {
  final val allAnimations: ArrayList<Animation> = ArrayList()

  init {
    for (animation in animationRepository.animations) {
      this.allAnimations.add(animation)
    }
  }

  /*fun createAnimation(name: String, script: String): Animation
  {
      // Create instance
      val animation = Animation("", "")
      animation.id = StringIdGenerator.generateUniqueId(this.animationRepository, name, this.animationServiceConfig.maxIdLength)
      animation.name = name
      animation.script = script

      // Save in repository
      this.animationRepository.save(animation)

      // Add instance to list
      this.allAnimations.add(animation)

      return animation
  }*/

  /*fun deleteAnimation(animation: Animation)
  {
      this.animationRepository.delete(animation)
      this.allAnimations.remove(animation)
  }*/
}
