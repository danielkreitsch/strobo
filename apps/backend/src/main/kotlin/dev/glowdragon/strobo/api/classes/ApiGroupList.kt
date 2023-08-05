package dev.glowdragon.strobo.api.classes

import com.fasterxml.jackson.annotation.JsonProperty
import dev.glowdragon.strobo.group.Group

class ApiGroupList(groups: List<Group>) {
  @JsonProperty("groups") val apiGroups: ArrayList<ApiGroup> = ArrayList()

  init {
    for (group in groups) {
      apiGroups.add(ApiGroup(group))
    }
  }
}
