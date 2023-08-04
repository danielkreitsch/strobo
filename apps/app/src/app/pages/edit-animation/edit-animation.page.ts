import {Component, OnInit} from '@angular/core';
import {Animation} from "../../domain/animation";
import {NavController} from "@ionic/angular";
import {AnimationService} from "../../services/animation.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'strobo-edit-animation',
  templateUrl: './edit-animation.page.html',
  styleUrls: ['./edit-animation.page.scss'],
})
export class EditAnimationPage implements OnInit
{
  animation?: Animation

  constructor(
    private navController: NavController,
    private animationService: AnimationService,
    private route: ActivatedRoute
  )
  {
  }

  ngOnInit()
  {
  }

  ionViewWillEnter()
  {
    this.animationService.getAnimation(this.route.snapshot.paramMap.get('id')!).then(animation =>
    {
      this.animation = animation
    })
  }

  onSaveClick()
  {
    this.animationService.updateAnimation(this.animation!).then(() =>
    {
      this.animationService.invalidateCache()
      this.navController.back()
    })
  }

  onCancelClick()
  {
    this.navController.back()
  }
}
