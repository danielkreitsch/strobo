package com.danielkreitsch.strobo.backend.device

import org.springframework.data.repository.CrudRepository

interface DeviceRepository : CrudRepository<DeviceEntity, String>
{
}
