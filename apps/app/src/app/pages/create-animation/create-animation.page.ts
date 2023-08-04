import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { AnimationService } from '../../services/animation.service';

@Component({
  selector: 'strobo-create-animation',
  templateUrl: './create-animation.page.html',
  styleUrls: ['./create-animation.page.scss'],
})
export class CreateAnimationPage implements OnInit {
  name?: string;
  script?: string;

  constructor(
    private navController: NavController,
    private animationService: AnimationService
  ) {}

  ngOnInit() {}

  onCreateClick() {
    // Create the animation and then go back
    this.animationService.createAnimation(this.name, this.script).then(() => {
      this.animationService.invalidateCache();
      this.navController.back();
    });
  }

  onCancelClick() {
    // Go back
    this.navController.back();
  }
}
