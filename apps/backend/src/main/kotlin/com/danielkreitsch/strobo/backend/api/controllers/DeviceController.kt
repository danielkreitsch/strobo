package com.danielkreitsch.strobo.backend.api.controllers

import com.danielkreitsch.strobo.backend.animation.AnimationService
import com.danielkreitsch.strobo.backend.api.classes.ApiDevice
import com.danielkreitsch.strobo.backend.api.classes.ApiDeviceList
import com.danielkreitsch.strobo.backend.api.classes.RegisterDeviceParams
import com.danielkreitsch.strobo.backend.api.classes.UpdateDeviceParams
import com.danielkreitsch.strobo.backend.device.Device
import com.danielkreitsch.strobo.backend.device.DeviceRepository
import com.danielkreitsch.strobo.backend.device.DeviceService
import com.danielkreitsch.strobo.backend.api.exceptions.NotFoundException
import com.danielkreitsch.strobo.backend.group.GroupService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("devices")
class DeviceController(
    private val deviceService: DeviceService,
    private val groupService: GroupService,
    private val animationService: AnimationService,
    private val deviceRepository: DeviceRepository
)
{
    @GetMapping
    fun getDevices(@RequestParam connected: Boolean?, @RequestParam registered: Boolean?, @RequestParam inAGroup: Boolean?): ApiDeviceList
    {
        var devices: MutableList<Device> = ArrayList()

        devices.addAll(this.deviceService.allDevices)

        if (connected != null)
        {
            devices = devices.filter { device -> device.connected == connected }.toMutableList()
        }

        if (registered != null)
        {
            devices = devices.filter { device -> device.registered == registered }.toMutableList()
        }

        if (inAGroup != null)
        {
            for (group in this.groupService.allGroups)
            {
                if (inAGroup == true)
                {
                    // TODO: Implement
                    //devices.filter { device -> group.devices.any { groupDevice -> groupDevice.info.id == device.info.id } }
                }
                else
                {
                    devices.removeIf { device -> group.devices.any { groupDevice -> groupDevice.entity.id == device.entity.id } }
                }
            }
        }

        return ApiDeviceList(devices.sortedBy { device -> device.sortOrder })
    }

    @GetMapping("/{id}")
    fun getDevice(@PathVariable id: String): ApiDevice
    {
        val device = deviceService.allDevices.firstOrNull { device -> device.id == id }
            ?: throw NotFoundException()

        return ApiDevice(device)
    }

    @PostMapping("/{id}/register")
    fun registerDevice(@PathVariable id: String, @RequestBody deviceParams: RegisterDeviceParams)
    {
        val device = this.deviceService.getDevice(id)
            ?: throw NotFoundException()

        // Set name
        var name = deviceParams.name
        if (name.isEmpty())
        {
            name = "Strobo Streifen"
        }
        val nameWithoutNumber = name
        var extraNumber = 2
        while (this.deviceService.allDevices.any { otherDevice -> otherDevice.entity.id != device.entity.id && otherDevice.registered && otherDevice.entity.name == name })
        {
            name = nameWithoutNumber + extraNumber
            extraNumber++
        }
        device.name = name

        device.registered = true

        this.deviceRepository.save(device.entity)
    }

    @PutMapping("/{id}")
    fun updateDevice(@PathVariable id: String, @RequestBody params: UpdateDeviceParams)
    {
        val device = this.deviceService.getDevice(id)
            ?: throw NotFoundException()

        if (params.name != null)
        {
            device.entity.name = params.name
        }

        if (params.active != null)
        {
            device.enabled = params.active

            /*val group = this.groupService.getGroupOfDevice(device)
            if (group != null)
            {
                group.active = group.devices.any { groupDevice -> groupDevice.registered && groupDevice.connected && groupDevice.active }
            }*/
        }

        if (params.brightness != null)
        {
            device.brightness = params.brightness
        }

        if (params.color != null)
        {
            device.color.apply(params.color)
        }

        val animationParams = HashMap<String, Any>()
        animationParams["rainbow"] = true
        device.animation = this.animationService.allAnimations.firstOrNull { animation -> animation.id == params.animationId }?.newInstance(animationParams)

        if (params.timeAdjustment != null)
        {
            device.timeAdjustment = params.timeAdjustment
        }

        if (params.attributes != null)
        {
            for (attribute in params.attributes)
            {
                device.attributes[attribute.key] = attribute.value
            }
        }

        this.deviceRepository.save(device.entity)
    }

    @PostMapping("/{id}/unregister")
    fun removeDevice(@PathVariable id: String)
    {
        val device = this.deviceService.getDevice(id)
            ?: throw NotFoundException()

        this.deviceRepository.delete(device.entity)
        device.registered = false

        this.groupService.getGroupOfDevice(device)?.devices?.remove(device)
    }
}
