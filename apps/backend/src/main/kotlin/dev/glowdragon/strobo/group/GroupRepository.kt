package dev.glowdragon.strobo.group

import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<GroupEntity, String> {}
