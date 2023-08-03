import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {AddDevicePageRoutingModule} from './register-device-routing.module';

import {RegisterDevicePage} from './register-device.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddDevicePageRoutingModule
  ],
  declarations: [RegisterDevicePage]
})
export class AddDevicePageModule {}
