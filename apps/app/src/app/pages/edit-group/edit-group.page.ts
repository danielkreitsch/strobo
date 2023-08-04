import {Component, OnInit} from "@angular/core";
import {Group} from "../../domain/group";
import {AlertController, NavController} from "@ionic/angular";
import {GroupService} from "../../services/group.service";
import {ActivatedRoute} from "@angular/router";
import {Device} from "../../domain/device";
import {DeviceService} from "../../services/device.service";

@Component({
  selector: "strobo-edit-group",
  templateUrl: "./edit-group.page.html",
  styleUrls: ["./edit-group.page.scss"],
})
export class EditGroupPage implements OnInit {
  group?: Group
  devices: Device[] = []
  deviceChecked: boolean[] = []

  nameMissingErrorDisplayed = false

  constructor(
    private navController: NavController,
    private alertController: AlertController,
    private groupService: GroupService,
    private deviceService: DeviceService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.groupService.getGroup(this.route.snapshot.paramMap.get("id")!).then(group => {
      this.group = group

      this.deviceService.getRegisteredGrouplessDevices().then(devices => {
        this.devices = []
        for (const device of this.group!.devices) {
          this.devices.push(device)
        }
        for (const device of devices) {
          this.devices.push(device)
        }

        this.deviceChecked = new Array<boolean>(this.devices.length).fill(false)
        this.devices.forEach((device, index) => {
          this.deviceChecked[index] = this.group!.devices.some(groupDevice => groupDevice.id == device.id)
        })
      })
    })
  }

  async onSaveClick() {
    if (this.group == null) {
      return
    }

    if (this.group.name!.trim().length == 0) {
      this.nameMissingErrorDisplayed = true
      return
    }

    // Apply selected devices
    this.group.devices = []
    this.devices.forEach((device, index) => {
      if (this.deviceChecked[index]) {
        this.group!.devices.push(device)
      }
    })

    if (this.group.devices.length == 0) {
      const alert = await this.alertController.create({
        header: "Leere Gruppe",
        message: "Es muss mindestens ein Gerät ausgewählt sein",
        buttons: [
          {
            text: "Okay",
            role: "cancel"
          }
        ]
      })
      await alert.present()
      return
    }

    // Save and then go back
    this.groupService.updateGroup(this.group).then(() => {
      this.groupService.invalidateCache()
      this.navController.back()
    })
  }

  onCancelClick() {
    this.navController.back()
  }

  updateGroupName(group: Group, event: any) {
    group.name = (event.currentTarget as HTMLInputElement).value
    this.nameMissingErrorDisplayed = false
  }
}
