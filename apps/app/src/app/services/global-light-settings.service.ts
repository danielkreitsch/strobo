import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http"
import {Device} from "../domain/device"
import {environment} from "../../environments/environment"

@Injectable({
    providedIn: 'root'
})
export class GlobalLightSettingsService
{
    constructor(
        private http: HttpClient
    )
    {
    }

    setBpm(bpm: number)
    {
        return new Promise(resolve =>
        {
            this.http.post(environment.backend.serviceUrl + '/bpm', {bpm}).subscribe(() =>
            {
                resolve(null)
            })
        })
    }
}
