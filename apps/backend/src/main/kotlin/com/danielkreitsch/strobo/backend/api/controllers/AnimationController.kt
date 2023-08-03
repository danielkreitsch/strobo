package com.danielkreitsch.strobo.backend.api.controllers

import com.danielkreitsch.strobo.backend.animation.AnimationRepository
import com.danielkreitsch.strobo.backend.animation.AnimationService
import com.danielkreitsch.strobo.backend.api.classes.ApiAnimation
import com.danielkreitsch.strobo.backend.api.classes.ApiAnimationList
import com.danielkreitsch.strobo.backend.api.exceptions.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/animations")
class AnimationController(
    private val animationService: AnimationService,
    private val animationRepository: AnimationRepository
)
{
    @GetMapping
    fun getAnimations(): ApiAnimationList
    {
        val animations = this.animationService.allAnimations.toTypedArray()
        return ApiAnimationList(animations)
    }

    @GetMapping("/{id}")
    fun getAnimation(@PathVariable id: String): ApiAnimation
    {
        val animation = this.animationService.allAnimations.firstOrNull { animation -> animation.id == id }
            ?: throw NotFoundException()

        return ApiAnimation(animation)
    }

    /*@PostMapping
    fun createAnimation(@RequestBody params: CreateAnimationParams): ApiAnimation
    {
        val animation = this.animationService.createAnimation(params.name, params.script)
        return ApiAnimation(animation)
    }*/

    /*@PutMapping("/{id}")
    fun updateAnimation(@PathVariable id: String, @RequestBody params: UpdateAnimationParams)
    {
        val animation = this.animationService.allAnimations.firstOrNull { animation -> animation.id == id }
            ?: throw NotFoundException()

        if (params.name != null)
        {
            animation.name = params.name
        }

        if (params.script != null)
        {
            animation.script = params.script
        }

        this.animationRepository.save(animation)
    }*/

    /*@DeleteMapping("/{id}")
    fun deleteAnimation(@PathVariable id: String)
    {
        val animation = this.animationService.allAnimations.firstOrNull { animation -> animation.id == id }
            ?: throw NotFoundException()

        this.animationService.deleteAnimation(animation)
    }*/
}
