import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http"
import {environment} from "../../../environments/environment"
import {AuthService} from "../../services/auth.service"

@Component({
    selector: 'strobo-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss']
})
export class LoginPage implements OnInit
{
    passwordErrorVisible = false
    passwordInputText = ""

    constructor(
        private authService: AuthService
    )
    {
    }

    ngOnInit()
    {
    }

    onSubmit()
    {
        this.authService.login(this.passwordInputText).then(success =>
        {
            if (!success)
            {
                this.passwordErrorVisible = true
            }
        })
    }

    onPasswordChange(event: any)
    {
        this.passwordInputText = (event.currentTarget as HTMLInputElement).value
        this.passwordErrorVisible = false
    }
}
