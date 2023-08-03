package com.danielkreitsch.strobo.backend.api.controllers

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class IndexController
{
    @GetMapping
    fun index(@RequestParam connected: Boolean?, @RequestParam registered: Boolean?, @RequestParam inAGroup: Boolean?): String
    {
        return "Strobo Backend"
    }
}

