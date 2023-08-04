import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Subject} from "rxjs";
import {Group} from "../domain/group";
import {GroupList} from "../domain/group-list";
import {environment} from "../../environments/environment";
import {Color} from "../domain/color";

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  constructor(
    private http: HttpClient
  ) {
  }

  onInvalidateCache = new Subject()

  invalidateCache() {
    this.onInvalidateCache.next(null)
  }

  getAllGroups(): Promise<Group[]> {
    return new Promise(resolve => {
      this.http.get<GroupList>(environment.backend.serviceUrl + '/groups').subscribe(result => {
        resolve(result.groups!)
      })
    })
  }

  getGroup(id: string): Promise<Group> {
    return new Promise(resolve => {
      this.http.get<Group>(environment.backend.serviceUrl + '/groups/' + id).subscribe(group => {
        resolve(group)
      })
    })
  }

  createGroup(name: string, deviceIds: string[]): Promise<null> {
    return new Promise(resolve => {
      this.http.post(environment.backend.serviceUrl + '/groups',
        new CreateGroupParams(name, deviceIds)).subscribe(() => {
        resolve(null)
      })
    })
  }

  updateGroup(group: Group): Promise<null> {
    return new Promise(resolve => {
      const deviceIds = new Array<string>(group.devices!.length)
      for (const device of group.devices!) {
        deviceIds.push(device.id!)
      }

      this.http.put(environment.backend.serviceUrl + '/groups/' + group.id,
        new UpdateGroupParams(group.name, deviceIds, group.active, group.brightness, group.color, group.animation?.id)).subscribe(() => {
        resolve(null)
      });
    });
  }

  deleteGroup(id: string): Promise<null> {
    return new Promise(resolve => {
      this.http.delete(environment.backend.serviceUrl + '/groups/' + id).subscribe(() => {
        resolve(null)
      });
    });
  }
}

export class CreateGroupParams {
  constructor(
    public name: string,
    public deviceIds: string[]
  ) {
  }
}

export class UpdateGroupParams {
  constructor(
    public name?: string,
    public deviceIds?: string[],
    public active?: boolean,
    public brightness?: number,
    public color?: Color,
    public animationId?: string
  ) {
  }
}
