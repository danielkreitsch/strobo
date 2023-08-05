package dev.glowdragon.strobo.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException : RuntimeException {
  constructor() : super() {}

  constructor(message: String) : super(message) {}
}
