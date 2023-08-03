package com.danielkreitsch.strobo.backend.api.classes

data class CreateGroupParams(
    val name: String,
    val deviceIds: Array<String>
)
