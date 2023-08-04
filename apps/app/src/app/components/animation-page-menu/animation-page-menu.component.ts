import { Component, OnInit } from '@angular/core';
import { Animation } from '../../domain/animation';
import {
  AlertController,
  NavController,
  NavParams,
  PopoverController,
} from '@ionic/angular';
import { AnimationService } from '../../services/animation.service';

@Component({
  selector: 'strobo-animation-page-menu',
  templateUrl: './animation-page-menu.component.html',
  styleUrls: ['./animation-page-menu.component.scss'],
})
export class AnimationPageMenuComponent implements OnInit {
  animation: Animation;

  constructor(
    private popoverController: PopoverController,
    private alertController: AlertController,
    private navController: NavController,
    private animationService: AnimationService,
    navParams: NavParams
  ) {
    this.animation = navParams.get('animation');
  }

  ngOnInit() {}

  onEditClick() {
    this.popoverController.dismiss();
    this.navController.navigateForward(
      '/animations/' + this.animation.id + '/edit'
    );
  }

  async onDeleteClick() {
    this.popoverController.dismiss();

    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: this.animation.name + ' löschen?',
      message: 'Das zugehörige Skript geht mit der Löschung auch verloren.',
      buttons: [
        {
          text: 'Abbrechen',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {},
        },
        {
          text: 'Löschen',
          handler: () => {
            this.animationService
              .deleteAnimation(this.animation.id!)
              .then(() => {
                this.navController.navigateRoot(['/animations']);
              });
          },
        },
      ],
    });

    await alert.present();
  }
}
