package com.danielkreitsch.strobo.backend.api.classes

import com.danielkreitsch.strobo.backend.animation.Animation

class ApiAnimation(
    private val animation: Animation
)
{
    val id: String
        get() = animation.id

    val name: String
        get() = animation.name
}
