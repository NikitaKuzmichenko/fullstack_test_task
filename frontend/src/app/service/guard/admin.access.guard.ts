import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Page, PageNavigator } from '../page.navigation.service';

@Injectable({
  providedIn: 'root'
})
export class AdminAccessGuard implements CanActivate{

    private readonly ACCESSPTED_ROLES : string[] = ["Administrator"];

    constructor(private service : AuthService, private router: Router,private pageNavigator: PageNavigator) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
    boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        let user = this.service.getCurrentUser();
        
        if(user == null || !this.ACCESSPTED_ROLES.includes(user.role)){
            return this.router.createUrlTree([this.pageNavigator.getPath(Page.LoginPage)]);
        }

        return true;
    }
}