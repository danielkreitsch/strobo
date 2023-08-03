import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {GroupPageRoutingModule} from './group-routing.module';

import {GroupPage} from './group.page';
import {SharedModule} from "../../shared/shared.module";
import {GroupPageMenuComponent} from "../../components/group-page-menu/group-page-menu.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GroupPageRoutingModule,
    SharedModule
  ],
  declarations: [GroupPage, GroupPageMenuComponent]
})
export class GroupPageModule
{
}
