import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { MusicSettingsPageRoutingModule } from './music-settings-routing.module';

import { MusicSettingsPage } from './music-settings.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    MusicSettingsPageRoutingModule,
  ],
  declarations: [MusicSettingsPage],
})
export class MusicSettingsPageModule {}
