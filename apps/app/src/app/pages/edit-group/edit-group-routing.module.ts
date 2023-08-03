import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EditGroupPage} from './edit-group.page';

const routes: Routes = [
  {
    path: '',
    component: EditGroupPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EditGroupPageRoutingModule {}
