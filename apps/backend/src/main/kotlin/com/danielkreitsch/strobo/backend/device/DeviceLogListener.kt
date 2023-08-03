package com.danielkreitsch.strobo.backend.device

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["device-log"])
class DeviceLogListener(
    private val deviceService: DeviceService
)
{
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitHandler
    fun receive(message: String, idk: ByteArray)
    {
        this.logger.info("Debug message: $message")
    }
}
