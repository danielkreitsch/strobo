import {Component, OnInit} from "@angular/core";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../domain/device";
import {ActivatedRoute} from "@angular/router";
import {NavController} from "@ionic/angular";

@Component({
  selector: "strobo-edit-device",
  templateUrl: "./edit-device.page.html",
  styleUrls: ["./edit-device.page.scss"],
})
export class EditDevicePage implements OnInit {
  device?: Device

  nameMissingErrorDisplayed = false

  constructor(
    private navController: NavController,
    private deviceService: DeviceService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.deviceService.getDevice(this.route.snapshot.paramMap.get("id")!).then(device => {
      this.device = device
    })
  }

  onSaveClick() {
    if (this.device == null) {
      return
    }

    if (this.device.name!.trim().length == 0) {
      this.nameMissingErrorDisplayed = true
      return
    }

    this.deviceService.updateDevice(this.device).then(() => {
      this.deviceService.invalidateCache()
      this.navController.back()
    })
  }

  onCancelClick() {
    this.navController.back()
  }

  updateDeviceName(device: Device, event: any) {
    device.name = (event.currentTarget as HTMLInputElement).value
    this.nameMissingErrorDisplayed = false
  }
}
