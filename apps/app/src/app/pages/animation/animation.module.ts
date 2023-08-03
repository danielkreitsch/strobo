import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {AnimationPageRoutingModule} from './animation-routing.module';

import {AnimationPage} from './animation.page';
import {AnimationPageMenuComponent} from "../../components/animation-page-menu/animation-page-menu.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AnimationPageRoutingModule
  ],
  declarations: [AnimationPage, AnimationPageMenuComponent]
})
export class AnimationPageModule {}
