import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateAnimationPageRoutingModule } from './create-animation-routing.module';

import { CreateAnimationPage } from './create-animation.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateAnimationPageRoutingModule,
  ],
  declarations: [CreateAnimationPage],
})
export class CreateAnimationPageModule {}
