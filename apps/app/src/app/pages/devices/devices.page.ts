import {Component, OnInit} from '@angular/core';
import {Group} from "../../domain/group";
import {NavController, PopoverController} from "@ionic/angular";
import {GroupService} from "../../services/group.service";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../domain/device";
import {DevicePageMenuComponent} from "../../components/device-page-menu/device-page-menu.component";
import {GroupPageMenuComponent} from "../../components/group-page-menu/group-page-menu.component";
import {DevicesPageMenuComponent} from "../../components/devices-page-menu/devices-page-menu.component";
import {Color} from "../../domain/color"
import {GlobalLightSettingsService} from "../../services/global-light-settings.service"

@Component({
    selector: 'strobo-groups',
    templateUrl: './devices.page.html',
    styleUrls: ['./devices.page.scss']
})
export class DevicesPage implements OnInit
{
    grouplessDevices: Device[] = []
    groups: Group[] = []
    buttonActionDisabled = false
    lastPressEvent = null
    popoverLock = false
    disableButtonTimer?: number
    blockGroupToggleReaction = false

    quotes = [
        "Hier könnte man Zitate anzeigen 🤔 <small><i>- Dani</i></small>",
        "London, #Ėýêæh¿ <small><i>- Travis Scott</i></small>",
    ]
    quotesString = ""

    disableButtonAction(time: number)
    {
        this.buttonActionDisabled = true

        if (this.disableButtonTimer != null)
        {
            clearTimeout(this.disableButtonTimer)
        }

        this.disableButtonTimer = setTimeout(() =>
        {
            this.buttonActionDisabled = false
        }, time)
    }

    constructor(
        private navController: NavController,
        private deviceService: DeviceService,
        private groupService: GroupService,
        private globalLightSettingsService: GlobalLightSettingsService,
        private popoverController: PopoverController
    )
    {
    }

    ngOnInit()
    {
        this.deviceService.onInvalidateCache.subscribe(() =>
        {
            this.fetchGrouplessDevices()
            this.fetchGroups()
        })

        this.groupService.onInvalidateCache.subscribe(() =>
        {
            this.fetchGroups()
            this.fetchGrouplessDevices()
        })

        this.switchQuote()
    }

    ionViewWillEnter()
    {
        this.fetchGrouplessDevices()
        this.fetchGroups()
    }

    onDeviceClick(device: Device)
    {
        if (this.buttonActionDisabled)
        {
            return
        }

        this.navController.navigateForward('/devices/' + device.id)
    }

    onDevicePress(event: any)
    {
        event.target = this.getParentWithClass(event.target, 'device-button')
        this.lastPressEvent = event
        this.popoverLock = false
    }

    onDeviceLongPress(device: Device)
    {
        this.presentDeviceContextMenu(device, this.lastPressEvent)
    }

    onDeviceToggleSwitch(group: Group, device: Device)
    {
        this.deviceService.updateDevice(device).then(() =>
        {
            /*if (group != null)
            {
              this.groupService.getGroup(group.id).then(newGroup =>
              {
                this.blockGroupToggleReaction = true
                setTimeout(() => this.blockGroupToggleReaction = false, 300)
                group.active = newGroup.active
              })
            }*/
        })
    }

    // Erstmal entfernt, da mehr verwirrend als hilfreich
    /*onGroupClick(group: Group)
    {
        if (!this.buttonActionDisabled)
        {
            this.navController.navigateForward('/groups/' + group.id)
        }
    }

    onGroupPress(event: any)
    {
        event.target = this.getParentWithClass(event.target, 'group-button')
        this.lastPressEvent = event
        this.popoverLock = false
    }

    onGroupLongPress(group: Group)
    {
        this.presentGroupContextMenu(group, this.lastPressEvent)
    }

    onGroupToggleSwitch(group: Group)
    {
        this.groupService.updateGroup(group).then(() =>
        {
            if (!this.blockGroupToggleReaction)
            {
                for (let device of group.devices)
                {
                    device.active = group.active
                }
            }
        })
    }*/

    onCookingModeClick()
    {
        const device = this.findDevice("kuechentheke")
        if (device != null)
        {
            console.log("found")
            if (device.active && device.color.r == 255 && device.color.g == 255 && device.color.b == 255 && device.color.w == 255 && device.animation == null)
            {
                device.active = false
            }
            else
            {
                device.active = true
                device.color = new Color(255, 255, 255, 255)
                device.animation = null
            }
            this.deviceService.updateDevice(device)
        }
    }

    findDevice(deviceId: string): Device | null
    {
        for (const device of this.grouplessDevices)
        {
            if (device.id == deviceId)
            {
                return device
            }
        }
        for (const group of this.groups)
        {
            for (const device of group.devices)
            {
                if (device.id == deviceId)
                {
                    return device
                }
            }
        }
        return null
    }

    /**
     * Fetch all devices without group from the webservice and update the offline list.
     */
    fetchGrouplessDevices()
    {
        this.deviceService.getRegisteredGrouplessDevices().then(grouplessDevices =>
        {
            this.grouplessDevices = grouplessDevices
        })
    }

    /**
     * Fetch all groups from the webservice and update the offline list.
     */
    fetchGroups()
    {
        this.groupService.getAllGroups().then(groups =>
        {
            this.groups = groups
        })
    }

    hasConnectedDevice(group: Group)
    {
        return group.devices.some(device => device.connected == true)
    }

    /**
     * Show the menu for adding a device or group.
     */
    async presentAddMenu(event: any)
    {
        const popover = await this.popoverController.create(
            {
                component: DevicesPageMenuComponent,
                cssClass: 'add-menu',
                event: event,
                translucent: true
            });

        await popover.present()
    }

    /**
     * Show the context menu.
     */
    async presentDeviceContextMenu(device: Device, event: any)
    {
        if (this.popoverLock)
        {
            return
        }

        this.popoverLock = true

        const popover = await this.popoverController.create(
            {
                component: DevicePageMenuComponent,
                cssClass: 'context-menu',
                event: event,
                translucent: true,
                showBackdrop: false,
                componentProps: {
                    "device": device
                }
            });

        await popover.present()
    }

    async presentGroupContextMenu(group: Group, event: any)
    {
        const popover = await this.popoverController.create(
            {
                component: GroupPageMenuComponent,
                cssClass: 'context-menu',
                event: event,
                translucent: true,
                showBackdrop: false,
                componentProps: {
                    "group": group
                }
            });

        await popover.present()
    }

    getParentWithClass(element: Element, className: string)
    {
        while (!element.classList.contains(className))
        {
            if (element.parentElement != null)
            {
                element = element.parentElement
            }
            else
            {
                break
            }
        }
        return element
    }

    /**
     * For a better performance.
     */
    trackGroups(index: number, group: Group)
    {
        return group.id
    }

    /**
     * For a better performance.
     */
    trackDevices(index: number, device: Device)
    {
        return device.id
    }

    switchQuote()
    {
        this.quotesString = ""
        let lastQuote = ""
        for (let i = 0; i < 50; i++)
        {
            const randomQuote = this.quotes[Math.floor(Math.random() * this.quotes.length)]
            if (randomQuote != lastQuote)
            {
                this.quotesString += randomQuote
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                lastQuote = randomQuote
            }
        }
    }

    onRefreshClick()
    {
        this.fetchGrouplessDevices()
        this.fetchGroups()
    }

    private beatSyncs: number[] = []

    onBeatSyncClick()
    {
        this.beatSyncs.push(performance.now())

        if (this.beatSyncs.length >= 2)
        {
            let intervalSum = 0.0
            for (let i = 0; i < this.beatSyncs.length - 1; i++)
            {
                intervalSum += this.beatSyncs[i + 1] - this.beatSyncs[i]
            }
            const averageInterval = intervalSum / (this.beatSyncs.length - 1)
            console.log("Interval: " + averageInterval + " ms (" + (this.beatSyncs.length - 1) + " values)")

            const bpm = 60 * 1000 / averageInterval
            this.globalLightSettingsService.setBpm(bpm)
            console.log("BPM: " + bpm)
        }

        if (this.beatSyncs.length >= 9)
        {
            this.beatSyncs.splice(0, 1)
        }
    }
}
