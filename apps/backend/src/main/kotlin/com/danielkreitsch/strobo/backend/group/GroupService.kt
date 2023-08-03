package com.danielkreitsch.strobo.backend.group

import com.danielkreitsch.strobo.backend.configuration.GroupServiceConfig
import com.danielkreitsch.strobo.backend.device.Device
import com.danielkreitsch.strobo.backend.device.DeviceService
import com.danielkreitsch.strobo.backend.util.StringIdGenerator
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupInfoRepository: GroupRepository,
    private val groupServiceConfig: GroupServiceConfig,
    private val deviceService: DeviceService
)
{
    final val allGroups: ArrayList<Group> = ArrayList()

    init
    {
        for (groupInfo in groupInfoRepository.findAll())
        {
            val group = Group(groupInfo)
            for (deviceId in groupInfo.deviceIds)
            {
                val device = this.deviceService.getDevice(deviceId) ?: continue
                group.devices.add(device)
            }
            this.allGroups.add(group)
        }
    }

    fun createGroup(name: String, deviceIds: Array<String>): Group
    {
        // Create info instance
        val groupInfo = GroupEntity()
        groupInfo.id = StringIdGenerator.generateUniqueId(this.groupInfoRepository, name, this.groupServiceConfig.maxIdLength)
        groupInfo.name = name
        groupInfo.deviceIds = deviceIds.toMutableList()

        // Save in repository
        this.groupInfoRepository.save(groupInfo)

        // Create wrapper instance
        val group = Group(groupInfo)
        for (deviceId in groupInfo.deviceIds)
        {
            val device = this.deviceService.getDevice(deviceId) ?: continue
            group.devices.add(device)
        }

        // Add instance to list
        this.allGroups.add(group)

        return group
    }

    fun getGroupOfDevice(device: Device): Group?
    {
        return allGroups.firstOrNull { group -> group.devices.contains(device) }
    }

    fun saveGroup(group: Group)
    {
        this.groupInfoRepository.save(group.entity)
    }

    fun refreshDeviceList(group: Group)
    {
        group.devices.clear()
        for (deviceId in group.entity.deviceIds)
        {
            val device = this.deviceService.getDevice(deviceId) ?: continue
            group.devices.add(device)
        }
    }

    fun deleteGroup(group: Group)
    {
        this.groupInfoRepository.delete(group.entity)
        this.allGroups.remove(group)
    }
}
