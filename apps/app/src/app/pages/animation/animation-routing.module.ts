import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AnimationPage } from './animation.page';

const routes: Routes = [
  {
    path: '',
    component: AnimationPage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AnimationPageRoutingModule {}
