package dev.glowdragon.strobo.animation

import org.springframework.stereotype.Component

@Component
class AnimationRepository {
  val animations: ArrayList<Animation> =
      arrayListOf(
          PulseAnimation(),
          FadeAnimation(),
          ColorPulseAnimation(),
          RainbowAnimation(),
          StroboAnimation(),
          MovingPointsAnimation(),
          RandomShotAnimation(),
          CenterPulseAnimation("center-pulse-1", "Center Pulse 1", false),
          CenterPulseAnimation("center-pulse-2", "Center Pulse 2", true),
          ColorSwitchAnimation(),
          ChristmasAnimation(),
          AdventCalendarAnimation())
}
