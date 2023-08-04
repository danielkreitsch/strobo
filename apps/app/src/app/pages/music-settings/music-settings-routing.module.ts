import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MusicSettingsPage } from './music-settings.page';

const routes: Routes = [
  {
    path: '',
    component: MusicSettingsPage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MusicSettingsPageRoutingModule {}
