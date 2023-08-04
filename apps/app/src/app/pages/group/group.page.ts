import {Component, OnInit} from '@angular/core';
import {Group} from "../../domain/group";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertController, PopoverController} from "@ionic/angular";
import {GroupService} from "../../services/group.service";
import {GroupPageMenuComponent} from "../../components/group-page-menu/group-page-menu.component";
import {Color} from "../../domain/color";
import {Animation} from "../../domain/animation";
import {DeviceService} from "../../services/device.service";
import {AnimationService} from "../../services/animation.service";

@Component({
  selector: 'strobo-group',
  templateUrl: './group.page.html',
  styleUrls: ['./group.page.scss'],
})
export class GroupPage implements OnInit {
  group?: Group
  animations: Animation[] = []

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private popoverController: PopoverController,
    private alertController: AlertController,
    private groupService: GroupService,
    private deviceService: DeviceService,
    private animationService: AnimationService
  ) {
  }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.groupService.getGroup(this.route.snapshot.paramMap.get('id')!).then(group => {
      this.group = group
    })

    this.animationService.getAllAnimations().then(animations => {
      this.animations = animations
    })
  }

  ionViewWillLeave() {
    this.groupService.invalidateCache()
  }

  /**
   * Change the color of the group and of all devices belonging to the group.
   * @param color The desired color.
   */
  setColor(color: Color | any) {
    if (this.group != null) {
      for (const device of this.group.devices) {
        device.color = color
      }

      this.group.color = color
      this.groupService.updateGroup(this.group)
    }
  }

  /**
   * Change the animation of the group and of all devices belonging to the group.
   * @param color The desired animation.
   */
  setAnimation(animation: Animation | null) {
    if (this.group != null) {
      this.group.animation = animation
      this.groupService.updateGroup(this.group)
    }

    /*for (let device of this.group.devices)
    {
      device.animation = animation
      this.deviceService.updateDevice(device)
    }*/
  }

  /**
   * Show the context menu.
   */
  async presentContextMenu(ev: any) {
    const popover = await this.popoverController.create(
      {
        component: GroupPageMenuComponent,
        cssClass: 'context-menu',
        event: ev,
        translucent: true,
        componentProps: {
          "group": this.group
        }
      });
    await popover.present()
  }
}
