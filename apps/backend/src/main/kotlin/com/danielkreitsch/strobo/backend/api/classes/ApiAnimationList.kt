package com.danielkreitsch.strobo.backend.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import com.danielkreitsch.strobo.backend.animation.Animation

class ApiAnimationList(animations: Array<Animation>)
{
    @JsonProperty("animations")
    val apiAnimations: ArrayList<ApiAnimation> = ArrayList()

    init
    {
        for (animation in animations)
        {
            apiAnimations.add(ApiAnimation(animation))
        }
    }
}
