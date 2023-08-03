import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {EditAnimationPageRoutingModule} from './edit-animation-routing.module';

import {EditAnimationPage} from './edit-animation.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EditAnimationPageRoutingModule
  ],
  declarations: [EditAnimationPage]
})
export class EditAnimationPageModule {}
