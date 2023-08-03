package com.danielkreitsch.strobo.backend.group

import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<GroupEntity, String>
{
}
