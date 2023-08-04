import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'overview',
        loadChildren: () =>
          import('../overview/overview.module').then(
            (m) => m.OverviewPageModule
          ),
      },
      {
        path: 'music',
        loadChildren: () =>
          import('../music/music.module').then((m) => m.MusicPageModule),
      },
      {
        path: 'devices',
        loadChildren: () =>
          import('../devices/devices.module').then((m) => m.GroupsPageModule),
      },
      {
        path: 'animations',
        loadChildren: () =>
          import('../animations/animations.module').then(
            (m) => m.AnimationsPageModule
          ),
      },
      {
        path: '',
        redirectTo: '/devices',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
