package com.danielkreitsch.strobo.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StroboApplication

fun main(args: Array<String>) {
    runApplication<StroboApplication>(*args)
}
