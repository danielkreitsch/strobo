import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Animation } from '../domain/animation';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AnimationList } from '../domain/animation-list';

@Injectable({
  providedIn: 'root',
})
export class AnimationService {
  constructor(private http: HttpClient) {}

  onInvalidateCache = new Subject();

  invalidateCache() {
    this.onInvalidateCache.next(null);
  }

  getAllAnimations(): Promise<Animation[]> {
    return new Promise((resolve) => {
      this.http
        .get<AnimationList>(environment.backend.serviceUrl + '/animations')
        .subscribe((result) => {
          resolve(result.animations);
        });
    });
  }

  getAnimation(id: string): Promise<Animation> {
    return new Promise((resolve) => {
      this.http
        .get<Animation>(environment.backend.serviceUrl + '/animations/' + id)
        .subscribe((animation) => {
          resolve(animation);
        });
    });
  }

  createAnimation(name?: string, script?: string): Promise<null> {
    return new Promise((resolve) => {
      this.http
        .post(
          environment.backend.serviceUrl + '/animations',
          new CreateAnimationParams(name, script)
        )
        .subscribe(() => {
          resolve(null);
        });
    });
  }

  updateAnimation(animation: Animation): Promise<null> {
    return new Promise((resolve) => {
      this.http
        .put(
          environment.backend.serviceUrl + '/animations/' + animation.id,
          animation
        )
        .subscribe(() => {
          resolve(null);
        });
    });
  }

  deleteAnimation(id: string): Promise<null> {
    return new Promise((resolve) => {
      this.http
        .delete(environment.backend.serviceUrl + '/animations/' + id)
        .subscribe(() => {
          resolve(null);
        });
    });
  }
}

export class CreateAnimationParams {
  constructor(public name?: string, public script?: string) {}
}
