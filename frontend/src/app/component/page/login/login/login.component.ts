import { AuthService } from '../../../../service/auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorMsgName, ErrorMsgProvider } from 'src/app/utils/error.msg.provider';
import { PageNavigator } from 'src/app/service/page.navigation.service';
import { Page } from '../../../../service/page.navigation.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  errorMsg: string = "";
  errorPresent : boolean = false;

  login_form = new FormGroup({
    login: new FormControl('',[Validators.required,Validators.minLength(1)]),
    password: new FormControl('',[Validators.required,Validators.minLength(1)])
  })
  
  constructor(private authService : AuthService, private errorMsgProvider : ErrorMsgProvider, 
    private pageNavigator : PageNavigator) {}
  
  ngOnInit(): void {}

  submitForm():void{
    this.errorPresent = false;
    
    if(!this.login_form.valid){
      this.errorMsg = this.errorMsgProvider.getErrorByName(ErrorMsgName.LoginFormNotFilled);
      this.errorPresent = true;
      return;
    }

    this.authService.logIn({login : this.login_form.get("login")?.value!,password : this.login_form.get("password")?.value!})
    .subscribe({
      next: (e) => {
        this.pageNavigator.goToPage(Page.SesorsListPage);
      },
      error: (e) => {
        this.errorMsg = this.errorMsgProvider.getErrorByName(ErrorMsgName.AuthentificationError);
        this.errorPresent = true;
      }
    })
  }
}
