import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment"
import {HttpClient} from "@angular/common/http"
import {Router} from "@angular/router"
import {AngularFireAuth} from "@angular/fire/compat/auth"
import firebase from "firebase/compat"

@Injectable({
    providedIn: "root"
})
export class AuthService
{
    constructor(
        private fireAuth: AngularFireAuth,
        private httpClient: HttpClient,
        private router: Router
    )
    {
        this.fireAuth.authState.subscribe((user) =>
        {
            if (user)
            {
                user.getIdToken(true).then(idToken =>
                {
                    localStorage.setItem("user-token", idToken)
                    console.log("Refreshed token")
                })
            }
        });
    }

    login(password: string): Promise<boolean>
    {
        return new Promise(resolve =>
        {
            this.fireAuth.signInWithEmailAndPassword(environment.auth.defaultEmail, password).then(result =>
            {
                result.user!.getIdToken(true).then(idToken =>
                {
                    localStorage.setItem("user-token", idToken)
                    this.redirectToHome()
                    resolve(true)
                })
            }).catch(error =>
            {
                console.log("Error while logging in:")
                console.log(error)
                resolve(false)
            })
        })
    }

    logout()
    {
        this.fireAuth.signOut().then(() =>
        {
            this.redirectToLogin()
        })
    }

    redirectToLogin()
    {
        this.router.navigate(["/login"])
    }

    redirectToHome()
    {
        this.router.navigate(["/"])
    }
}
