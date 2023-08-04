import {Component, OnInit} from '@angular/core';
import {Group} from "../../domain/group";
import {AlertController, NavController, NavParams, PopoverController} from "@ionic/angular";
import {GroupService} from "../../services/group.service";
import {Router} from "@angular/router";

@Component({
  selector: 'strobo-group-page-menu',
  templateUrl: './group-page-menu.component.html',
  styleUrls: ['./group-page-menu.component.scss'],
})
export class GroupPageMenuComponent implements OnInit
{
  group: Group

  constructor(
    private popoverController: PopoverController,
    private alertController: AlertController,
    private navController: NavController,
    private router: Router,
    private groupService: GroupService,
    navParams: NavParams
  )
  {
    this.group = navParams.get('group')
  }

  ngOnInit()
  {
  }

  onEditClick()
  {
    this.popoverController.dismiss()
    this.navController.navigateForward('/groups/' + this.group.id + '/edit')
  }

  async onDeleteClick()
  {
    this.popoverController.dismiss()

    const alert = await this.alertController.create({
      cssClass: 'pop-over-style',
      header: this.group.name + ' auflösen?',
      message: 'Die Gruppe wird gelöscht, aber die Geräte bleiben eingerichtet.',
      buttons: [
        {
          text: 'Abbrechen',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () =>
          {
          }
        }, {
          text: 'Löschen',
          handler: () =>
          {
            this.groupService.deleteGroup(this.group.id!).then(() =>
            {
              this.groupService.invalidateCache()
              this.navController.navigateRoot(['/devices'])
            })
          }
        }
      ]
    });

    await alert.present()
  }
}
