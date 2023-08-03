import {Component, OnInit} from '@angular/core';
import {AlertController, NavController, NavParams, PopoverController} from "@ionic/angular";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../domain/device";
import {Router} from "@angular/router";

@Component({
  selector: 'app-device-page-menu',
  templateUrl: './device-page-menu.component.html',
  styleUrls: ['./device-page-menu.component.scss'],
})
export class DevicePageMenuComponent implements OnInit
{
  device: Device

  constructor(
    private popoverController: PopoverController,
    private alertController: AlertController,
    private navController: NavController,
    private router: Router,
    private deviceService: DeviceService,
    navParams: NavParams
  )
  {
    this.device = navParams.get('device')
  }

  ngOnInit()
  {
  }

  onEditClick()
  {
    this.popoverController.dismiss()
    this.navController.navigateForward('/devices/' + this.device.id + '/edit')
  }

  async onDeleteClick()
  {
    this.popoverController.dismiss()

    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: this.device.name + ' löschen?',
      message: 'Sie müssen das Gerät erneut hinzufügen, um es zukünftig steuern zu können.',
      buttons: [
        {
          text: 'Abbrechen',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () =>
          {
          }
        }, {
          text: 'Löschen',
          handler: () =>
          {
            this.deviceService.unregisterDevice(this.device.id).then(() =>
            {
              this.deviceService.invalidateCache()
               this.navController.navigateRoot(['/devices'])
            })
          }
        }
      ]
    });

    await alert.present()
  }
}
