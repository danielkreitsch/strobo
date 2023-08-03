import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertController, PopoverController} from "@ionic/angular";
import {Animation} from "../../domain/animation";
import {AnimationService} from "../../services/animation.service";
import {AnimationPageMenuComponent} from "../../components/animation-page-menu/animation-page-menu.component";

@Component({
  selector: 'app-animation',
  templateUrl: './animation.page.html',
  styleUrls: ['./animation.page.scss'],
})
export class AnimationPage implements OnInit
{
  animation: Animation

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private popoverController: PopoverController,
    private alertController: AlertController,
    private animationService: AnimationService
  )
  {
  }

  ngOnInit()
  {
  }

  ionViewWillEnter()
  {
    this.animationService.getAnimation(this.route.snapshot.paramMap.get('id')).then(animation =>
    {
      this.animation = animation
    })
  }

  ionViewWillLeave()
  {
    this.animationService.invalidateCache()
  }

  /**
   * Show the context menu.
   */
  async presentContextMenu(ev: any)
  {
    const popover = await this.popoverController.create(
      {
        component: AnimationPageMenuComponent,
        cssClass: 'context-menu',
        event: ev,
        translucent: true,
        componentProps: {
          "animation": this.animation
        }
      });
    await popover.present()
  }
}
