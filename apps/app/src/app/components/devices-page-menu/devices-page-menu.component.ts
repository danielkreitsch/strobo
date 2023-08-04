import { Component, OnInit } from '@angular/core';
import {
  AlertController,
  NavController,
  PopoverController,
} from '@ionic/angular';
import { DeviceService } from '../../services/device.service';

@Component({
  selector: 'strobo-devices-page-menu',
  templateUrl: './devices-page-menu.component.html',
  styleUrls: ['./devices-page-menu.component.scss'],
})
export class DevicesPageMenuComponent implements OnInit {
  constructor(
    private deviceService: DeviceService,
    private popoverController: PopoverController,
    private alertController: AlertController,
    private navController: NavController
  ) {}

  ngOnInit() {}

  onRegisterDeviceClick() {
    this.popoverController.dismiss();
    this.navController.navigateForward('/devices/new');
  }

  async onCreateGroupClick() {
    this.popoverController.dismiss();

    const grouplessDevices =
      await this.deviceService.getRegisteredGrouplessDevices();
    if (grouplessDevices.length > 0) {
      this.navController.navigateForward('/groups/new');
    } else {
      const alert = await this.alertController.create({
        message: 'Alle Geräte sind bereits in einer Gruppe',
        buttons: [
          {
            text: 'Okay',
            role: 'cancel',
          },
        ],
      });
      await alert.present();
      return;
    }
  }
}
