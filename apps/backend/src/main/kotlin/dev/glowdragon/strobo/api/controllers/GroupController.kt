package dev.glowdragon.strobo.api.controllers

import dev.glowdragon.strobo.animation.AnimationService
import dev.glowdragon.strobo.api.classes.*
import dev.glowdragon.strobo.api.exceptions.NotFoundException
import dev.glowdragon.strobo.device.DeviceService
import dev.glowdragon.strobo.group.GroupRepository
import dev.glowdragon.strobo.group.GroupService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService,
    private val deviceService: DeviceService,
    private val animationService: AnimationService,
    private val groupInfoRepository: GroupRepository
) {
  @GetMapping
  fun getGroups(): ApiGroupList {
    val groups = this.groupService.allGroups
    return ApiGroupList(groups.sortedBy { group -> group.sortOrder })
  }

  @GetMapping("/{id}")
  fun getGroup(@PathVariable id: String): ApiGroup {
    val group =
        this.groupService.allGroups.firstOrNull { group -> group.entity.id == id }
            ?: throw NotFoundException()

    return ApiGroup(group)
  }

  @PostMapping
  fun createGroup(@RequestBody params: CreateGroupParams): ApiGroup {
    val group = this.groupService.createGroup(params.name, params.deviceIds)
    return ApiGroup(group)
  }

  @PutMapping("/{id}")
  fun updateGroup(@PathVariable id: String, @RequestBody params: UpdateGroupParams) {
    val group =
        this.groupService.allGroups.firstOrNull { group -> group.entity.id == id }
            ?: throw NotFoundException()

    if (params.name != null) {
      group.entity.name = params.name
    }

    if (params.deviceIds != null) {
      group.entity.deviceIds = params.deviceIds.toMutableList()
      this.groupService.refreshDeviceList(group)
    }

    if (params.active != null) {
      group.active = params.active
      for (device in group.devices) {
        device.enabled = group.active
      }
    }

    if (params.brightness != null) {
      group.brightness = params.brightness
    }

    if (params.color != null) {
      group.color.apply(params.color)
      for (device in group.devices) {
        device.color.apply(group.color)
      }
    }

    val animationParams = HashMap<String, Any>()
    animationParams["rainbow"] = true

    group.animation =
        this.animationService.allAnimations
            .firstOrNull { animation -> animation.id == params.animationId }
            ?.newInstance(animationParams)
    for (device in group.devices) {
      device.animation = group.animation
    }

    this.groupService.saveGroup(group)
  }

  @DeleteMapping("/{id}")
  fun deleteGroup(@PathVariable id: String) {
    val group =
        this.groupService.allGroups.firstOrNull { group -> group.entity.id == id }
            ?: throw NotFoundException()

    this.groupService.deleteGroup(group)
  }
}
