import { Component, OnInit } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { Device } from '../../domain/device';
import { AlertController, NavController } from '@ionic/angular';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'strobo-add-device',
  templateUrl: './register-device.page.html',
  styleUrls: ['./register-device.page.scss'],
})
export class RegisterDevicePage implements OnInit {
  devices: Device[] = [];
  refreshInterval?: Subscription;

  constructor(
    private navController: NavController,
    private alertController: AlertController,
    private deviceService: DeviceService
  ) {}

  ngOnInit() {}

  ionViewWillEnter() {
    setTimeout(() => this.fetchDevices(), 250);

    this.refreshInterval = interval(1000).subscribe(() => {
      this.fetchDevices();
    });
  }

  ionViewWillLeave() {
    this.refreshInterval?.unsubscribe();
    this.deviceService.invalidateCache();
  }

  /**
   * Fetch all unregistered connected devices from the webservice and update the offline list.
   */
  fetchDevices() {
    this.deviceService.getConnectedUnregisteredDevices().then((devices) => {
      this.devices = devices;
    });
  }

  /**
   * Register the device in the webservice.
   */
  registerDevice(device: Device) {
    this.deviceService.registerDevice(device).then(() => {
      this.deviceService.invalidateCache();
      this.navController.back();
    });
  }

  /**
   * Show a form for the name of the new device.
   */
  async presentNamePrompt(device: Device) {
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Neues Gerät',
      inputs: [
        {
          name: 'name',
          type: 'text',
          placeholder: 'Strobo Streifen',
        },
      ],
      buttons: [
        {
          text: 'Abbrechen',
          role: 'cancel',
          cssClass: 'secondary',
        },
        {
          text: 'Hinzufügen',
          handler: (alertData) => {
            device.name = alertData.name;
            this.registerDevice(device);
          },
        },
      ],
    });

    alert.present().then(() => {
      const firstInput: any = document.querySelector('ion-alert input');
      firstInput.focus();
      return;
    });
  }

  /**
   * For a better performance.
   */
  trackDevices(index: number, device: Device) {
    return device.id;
  }
}
