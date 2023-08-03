import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EditDevicePage} from './edit-device.page';

const routes: Routes = [
  {
    path: '',
    component: EditDevicePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EditDevicePageRoutingModule {}
