package dev.glowdragon.strobo.device

import org.springframework.data.repository.CrudRepository

interface DeviceRepository : CrudRepository<DeviceEntity, String> {}
