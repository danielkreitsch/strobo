package com.danielkreitsch.strobo.backend.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import com.danielkreitsch.strobo.backend.group.Group

class ApiGroupList(groups: List<Group>)
{
    @JsonProperty("groups")
    val apiGroups: ArrayList<ApiGroup> = ArrayList()

    init
    {
        for (group in groups)
        {
            apiGroups.add(ApiGroup(group))
        }
    }
}
