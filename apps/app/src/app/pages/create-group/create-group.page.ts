import {Component, OnInit} from '@angular/core';
import {Device} from "../../domain/device";
import {DeviceService} from "../../services/device.service";
import {AlertController, NavController} from "@ionic/angular";
import {GroupService} from "../../services/group.service";

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.page.html',
  styleUrls: ['./create-group.page.scss'],
})
export class CreateGroupPage implements OnInit
{
  name: string = ""
  devices: Device[]
  checked: boolean[]

  nameMissingErrorDisplayed = false

  constructor(
    private navController: NavController,
    private groupService: GroupService,
    private deviceService: DeviceService,
    private alertController: AlertController
  )
  {
  }

  ngOnInit()
  {
  }

  ionViewWillEnter()
  {
    this.deviceService.getRegisteredGrouplessDevices().then(devices =>
    {
      this.devices = devices
      this.checked = new Array<boolean>(this.devices.length).fill(false)
    })
  }

  async onCreateClick()
  {
    if (this.name.trim().length == 0)
    {
      this.nameMissingErrorDisplayed = true
      return
    }

    // Add selected devices
    let deviceIds = new Array<string>()
    this.devices.forEach((device, index) =>
    {
      if (this.checked[index])
      {
        deviceIds.push(device.id)
      }
    })

    if (deviceIds.length == 0)
    {
      const alert = await this.alertController.create({
        header: 'Leere Gruppe',
        message: 'Es muss mindestens ein Gerät ausgewählt sein',
        buttons: [
          {
            text: 'Okay',
            role: 'cancel'
          }
        ]
      })
      await alert.present()
      return
    }

    // Create the group and then go back
    this.groupService.createGroup(this.name, deviceIds).then(() =>
    {
      this.groupService.invalidateCache()
      this.navController.back()
    })
  }

  onCancelClick()
  {
    this.navController.back()
  }

  onNameUpdate(event: any)
  {
    this.name = (event.target as HTMLInputElement).value
    this.nameMissingErrorDisplayed = false
  }
}
