import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Device } from '../domain/device';
import { DeviceList } from '../domain/device-list';
import { Subject } from 'rxjs';
import { Color } from '../domain/color';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  constructor(private http: HttpClient) {}

  onInvalidateCache = new Subject();

  invalidateCache() {
    this.onInvalidateCache.next(null);
  }

  getDevices(
    registered?: boolean,
    connected?: boolean,
    inAGroup?: boolean
  ): Promise<Device[]> {
    const params = new HttpParams();

    if (registered != null) {
      params.set('registered', registered);
    }

    if (connected != null) {
      params.set('connected', connected);
    }

    if (inAGroup != null) {
      params.set('inAGroup', inAGroup);
    }

    return new Promise((resolve) => {
      this.http
        .get<DeviceList>(environment.backend.serviceUrl + '/devices', {
          params: params,
        })
        .subscribe((result) => {
          resolve(result.devices);
        });
    });
  }

  getConnectedUnregisteredDevices(): Promise<Device[]> {
    return new Promise((resolve) => {
      this.http
        .get<DeviceList>(
          environment.backend.serviceUrl +
            '/devices?connected=true&registered=false'
        )
        .subscribe(
          (result) => {
            resolve(result.devices);
          },
          (error) => {
            resolve(new Array<Device>());
          }
        );
    });
  }

  getRegisteredDevices(): Promise<Device[]> {
    return new Promise((resolve) => {
      this.http
        .get<DeviceList>(
          environment.backend.serviceUrl + '/devices?registered=true'
        )
        .subscribe(
          (result) => {
            resolve(result.devices);
          },
          (error) => {
            resolve(new Array<Device>());
          }
        );
    });
  }

  getRegisteredGrouplessDevices(): Promise<Device[]> {
    return new Promise((resolve) => {
      this.http
        .get<DeviceList>(
          environment.backend.serviceUrl +
            '/devices?registered=true&inAGroup=false'
        )
        .subscribe(
          (result) => {
            resolve(result.devices);
          },
          (error) => {
            resolve(new Array<Device>());
          }
        );
    });
  }

  getDevice(deviceId: string): Promise<Device> {
    return new Promise((resolve) => {
      this.http
        .get<Device>(environment.backend.serviceUrl + '/devices/' + deviceId)
        .subscribe((device) => {
          resolve(device);
        });
    });
  }

  getCurrentDevice(): Promise<Device> {
    return new Promise((resolve) => {
      this.http
        .get<Device>(environment.backend.serviceUrl + '/devices/current')
        .subscribe((device) => {
          resolve(device);
        });
    });
  }

  registerDevice(device: Device) {
    return new Promise((resolve) => {
      this.http
        .post(
          environment.backend.serviceUrl +
            '/devices/' +
            device.id +
            '/register',
          device
        )
        .subscribe(() => {
          resolve(null);
        });
    });
  }

  updateDevice(device: Device) {
    return new Promise((resolve) => {
      this.http
        .put(
          environment.backend.serviceUrl + '/devices/' + device.id,
          new UpdateDeviceParams(
            device.name,
            device.active,
            device.brightness,
            device.color,
            device.animation?.id,
            device.timeAdjustment
          )
        )
        .subscribe(() => {
          resolve(null);
        });
    });
  }

  unregisterDevice(id: string) {
    return new Promise((resolve) => {
      this.http
        .post(
          environment.backend.serviceUrl + '/devices/' + id + '/unregister',
          null
        )
        .subscribe(() => {
          resolve(null);
        });
    });
  }
}

export class UpdateDeviceParams {
  constructor(
    public name?: string,
    public active?: boolean,
    public brightness?: number,
    public color?: Color,
    public animationId?: string,
    public timeAdjustment?: number
  ) {}
}
