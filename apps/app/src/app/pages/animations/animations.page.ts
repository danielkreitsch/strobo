import { Component, OnInit } from '@angular/core';
import { AnimationService } from '../../services/animation.service';
import { Animation } from '../../domain/animation';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'strobo-animations',
  templateUrl: './animations.page.html',
  styleUrls: ['./animations.page.scss'],
})
export class AnimationsPage implements OnInit {
  animations: Animation[] = [];

  constructor(
    private navController: NavController,
    private animationService: AnimationService
  ) {}

  ngOnInit() {
    this.animationService.onInvalidateCache.subscribe(() => {
      this.fetchAnimations();
    });
  }

  ionViewWillEnter() {
    this.fetchAnimations();
  }

  onAnimationClick(animation: Animation) {
    this.navController.navigateForward('/animations/' + animation.id);
  }

  /**
   * Fetch all animations from the webservice and update the offline list.
   */
  fetchAnimations() {
    this.animationService.getAllAnimations().then((animations) => {
      this.animations = animations;
    });
  }

  /**
   * For a better performance.
   */
  trackAnimations(index: number, animation: Animation) {
    return animation.id;
  }
}
