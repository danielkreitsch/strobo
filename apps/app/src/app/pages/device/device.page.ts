import {Component, OnInit} from '@angular/core';
import {Device} from "../../domain/device";
import {ActivatedRoute, Router} from "@angular/router";
import {DeviceService} from "../../services/device.service";
import {AlertController, PopoverController} from "@ionic/angular";
import {DevicePageMenuComponent} from "../../components/device-page-menu/device-page-menu.component";
import {Color} from "../../domain/color";
import {AnimationService} from "../../services/animation.service";
import {Animation} from "../../domain/animation";

@Component({
    selector: 'strobo-device',
    templateUrl: './device.page.html',
    styleUrls: ['./device.page.scss']
})
export class DevicePage implements OnInit
{
    colors: Color[] = [
        new Color(255, 255, 255, 255), // White
        new Color(255, 0, 0, 0), // Red
        new Color(255, 165, 0, 0), // Orange
        new Color(0, 255, 0, 0), // Green
        new Color(255, 255, 0, 0), // Yellow
        new Color(64, 224, 208, 0), // Aqua
        new Color(0, 0, 255, 0), // Blue
        new Color(134, 1, 175, 0) // Purple
    ]

    deviceOfflineImages: string[] = [
        "schlafender-bene/schlafender-bene-1.jpeg",
        "schlafender-bene/schlafender-bene-2.jpeg",
        "schlafender-bene/schlafender-bene-3.jpeg",
        "schlafender-bene/schlafender-bene-4.jpeg"
    ]

    device?: Device
    animations: Animation[] = []
    deviceOfflineImage = ""

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private popoverController: PopoverController,
        private alertController: AlertController,
        private deviceService: DeviceService,
        private animationService: AnimationService
    )
    {
    }

    ngOnInit()
    {
        this.deviceOfflineImage = this.deviceOfflineImages[Math.floor(Math.random() * this.deviceOfflineImages.length)]
    }

    ionViewWillEnter()
    {
        this.fetchDevice()

        this.animationService.getAllAnimations().then(animations =>
        {
            this.animations = animations
        })
    }

    ionViewWillLeave()
    {
        this.deviceService.invalidateCache()
    }

    onDeviceToggleSwitch(device: Device)
    {
        this.deviceService.updateDevice(device)
    }

    /**
     * Fetch device data from the webservice and update the offline instance.
     */
    fetchDevice()
    {
        this.deviceService.getDevice(this.route.snapshot.paramMap.get('id')!).then(device =>
        {
            this.device = device
        })
    }

    /**
     * Change the color of the device.
     * @param color The desired color.
     */
    setColor(color: Color)
    {
        if (this.device != null)
        {
            this.device.color = color
            this.deviceService.updateDevice(this.device)
        }
    }

    isSameColor(color1: Color, color2: Color)
    {
        return color1.r == color2.r && color1.g == color2.g && color1.b == color2.b
    }

    /**
     * Change the animation of the device.
     * @param animation The desired animation.
     */
    setAnimation(animation: Animation | null)
    {
        if (this.device != null)
        {
            this.device.animation = animation
            this.deviceService.updateDevice(this.device)
        }
    }

    setTimeAdjustment(event: any)
    {
        if (this.device != null)
        {
            this.device.timeAdjustment = (event.currentTarget as HTMLInputElement).valueAsNumber
            this.deviceService.updateDevice(this.device)
        }
    }

    /**
     * Show the context menu.
     */
    async presentContextMenu(ev: any)
    {
        const popover = await this.popoverController.create(
            {
                component: DevicePageMenuComponent,
                cssClass: 'context-menu',
                event: ev,
                translucent: true,
                componentProps: {
                    "device": this.device
                }
            });
        await popover.present()
    }
}
