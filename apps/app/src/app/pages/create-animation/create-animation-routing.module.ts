import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {CreateAnimationPage} from './create-animation.page';

const routes: Routes = [
  {
    path: '',
    component: CreateAnimationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateAnimationPageRoutingModule {}
