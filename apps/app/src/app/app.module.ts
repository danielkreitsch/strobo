import {NgModule} from '@angular/core'
import {BrowserModule, HAMMER_GESTURE_CONFIG} from '@angular/platform-browser'
import {RouteReuseStrategy} from '@angular/router'
import {IonicModule, IonicRouteStrategy} from '@ionic/angular'
import {AppRoutingModule} from './app-routing.module'
import {AppComponent} from './app.component'
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http'
import {ServiceWorkerModule} from '@angular/service-worker'
import {environment} from '../environments/environment'
import {IonicGestureConfig} from './utils/ionic-gesture-config'
import {TokenInterceptor} from "./interceptors/token.interceptor"
import {AngularFireModule} from "@angular/fire/compat"
import {AngularFireAuthModule} from "@angular/fire/compat/auth"

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        IonicModule.forRoot(),
        AppRoutingModule,
        HttpClientModule,
        ServiceWorkerModule.register('ngsw-worker.js', {
            enabled: environment.production,
            registrationStrategy: 'registerWhenStable:30000'
        }),
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireAuthModule],
    providers: [
        {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
        {provide: HAMMER_GESTURE_CONFIG, useClass: IonicGestureConfig},
        {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule
{
}
