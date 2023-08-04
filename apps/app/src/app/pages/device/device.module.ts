import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DevicePageRoutingModule } from './device-routing.module';

import { DevicePage } from './device.page';
import { SharedModule } from '../../shared/shared.module';
import { DevicePageMenuComponent } from '../../components/device-page-menu/device-page-menu.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DevicePageRoutingModule,
    SharedModule,
  ],
  declarations: [DevicePage, DevicePageMenuComponent],
})
export class DevicePageModule {}
