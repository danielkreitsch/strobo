import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'login',
    loadChildren: () =>
      import('./pages/login/login.module').then((m) => m.LoginPageModule),
  },
  {
    path: 'logout',
    loadChildren: () =>
      import('./pages/logout/logout.module').then((m) => m.LogoutPageModule),
  },
  {
    path: 'music/settings',
    loadChildren: () =>
      import('./pages/music-settings/music-settings.module').then(
        (m) => m.MusicSettingsPageModule
      ),
  },
  {
    path: 'groups/new',
    loadChildren: () =>
      import('./pages/create-group/create-group.module').then(
        (m) => m.CreateGroupPageModule
      ),
  },
  {
    path: 'groups/:id/edit',
    loadChildren: () =>
      import('./pages/edit-group/edit-group.module').then(
        (m) => m.EditGroupPageModule
      ),
  },
  {
    path: 'groups/:id',
    loadChildren: () =>
      import('./pages/group/group.module').then((m) => m.GroupPageModule),
  },
  {
    path: 'devices/new',
    loadChildren: () =>
      import('./pages/register-device/register-device.module').then(
        (m) => m.AddDevicePageModule
      ),
  },
  {
    path: 'devices/:id/edit',
    loadChildren: () =>
      import('./pages/edit-device/edit-device.module').then(
        (m) => m.EditDevicePageModule
      ),
  },
  {
    path: 'devices/:id',
    loadChildren: () =>
      import('./pages/device/device.module').then((m) => m.DevicePageModule),
  },
  {
    path: 'animations/new',
    loadChildren: () =>
      import('./pages/create-animation/create-animation.module').then(
        (m) => m.CreateAnimationPageModule
      ),
  },
  {
    path: 'animations/:id/edit',
    loadChildren: () =>
      import('./pages/edit-animation/edit-animation.module').then(
        (m) => m.EditAnimationPageModule
      ),
  },
  {
    path: 'animations/:id',
    loadChildren: () =>
      import('./pages/animation/animation.module').then(
        (m) => m.AnimationPageModule
      ),
  },
  {
    path: '',
    loadChildren: () =>
      import('./pages/tabs/tabs.module').then((m) => m.TabsPageModule),
  },
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
