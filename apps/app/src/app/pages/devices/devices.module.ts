import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {GroupsPageRoutingModule} from './devices-routing.module';
import {DevicesPage} from './devices.page';
import {LongPressModule} from "ionic-long-press";
import {DevicesPageMenuComponent} from "../../components/devices-page-menu/devices-page-menu.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GroupsPageRoutingModule,
    LongPressModule
  ],
  declarations: [DevicesPage, DevicesPageMenuComponent]
})
export class GroupsPageModule
{
}
