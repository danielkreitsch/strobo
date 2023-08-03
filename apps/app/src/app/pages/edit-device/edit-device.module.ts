import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {EditDevicePageRoutingModule} from './edit-device-routing.module';
import {EditDevicePage} from './edit-device.page';
import {DeviceTypePipe} from "../../pipes/device-type.pipe";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EditDevicePageRoutingModule
  ],
  declarations: [EditDevicePage, DeviceTypePipe]
})
export class EditDevicePageModule {}
