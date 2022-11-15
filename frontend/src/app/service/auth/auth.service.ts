import { Injectable, OnInit } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs/internal/Observable";
import { catchError, map, tap } from 'rxjs/operators';
import { of } from "rxjs/internal/observable/of";
import { UserCredentials, UserInfo } from '../../entity/user';
import { throwError } from "rxjs";

const jsonHeader = new HttpHeaders({
  'Content-Type': 'application/json',
})

@Injectable({
    providedIn: 'root'
  })
export class AuthService implements OnInit{

  public readonly LOGIN_URL : string = 'http://localhost:8080/Test/login';
  public readonly REFRESH_TOKEN_URL : string = 'http://localhost:8080/Test/v1/auth/refreshToken';
  
  public readonly JWT_TOKEN : string = 'JWT';
  public readonly REFRESH_TOKEN : string = 'REFRESH_TOKEN';

  private currentUser : UserInfo | null;

  constructor(private http: HttpClient) {
    let token = this.getJwtToken();
    if(token != null){
      let decodedJwt = this.parseJwt(token);
      this.currentUser = this.parceUser(decodedJwt);
    }else{
      this.currentUser = null;
    }
  }

  ngOnInit(): void {}
  
  public logIn(uesr : UserCredentials) : Observable<boolean> {
    return this.http.post<any>(this.LOGIN_URL,uesr,{ headers: jsonHeader}).pipe(
      tap(responce => {
          return this.doLoginUser(responce.jwt, responce.refreshToken);
      }),
      map(e=>true),
      catchError(error => {
        return throwError(() => false);
      }));
  }

  public refreshToken() : Observable<boolean>{
    let token = this.getRefreshToken();
    if(token == null){
      return of(false);
    }

    return this.http.post<any>(this.REFRESH_TOKEN_URL,token,{ headers: jsonHeader}).pipe(
      tap(responce => {
          return this.doLoginUser(responce.jwt, responce.refreshToken);
      }),
      map(e=>true),
      catchError(error => {
        return throwError(() => false);
      }));
  }

  public isUserLogedIn() : boolean{
    let token = localStorage.getItem(this.JWT_TOKEN);
    if(token == null){
      return false;
    }
    return this.isTokenExperd(this.parseJwt(token));
  }

  public logOut(){
    this.currentUser = null;
    localStorage.removeItem(this.REFRESH_TOKEN);
    localStorage.removeItem(this.JWT_TOKEN);
  }

  public getRefreshToken() : string | null {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  public getJwtToken() : string | null {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  public getCurrentUser() : UserInfo | null {
    return this.currentUser;
  }

  private doLoginUser(jwt: string, refreshToken: string): void {
    localStorage.setItem(this.JWT_TOKEN,jwt);
    localStorage.setItem(this.REFRESH_TOKEN,refreshToken);

    let decodedJwt = this.parseJwt(jwt);
    this.currentUser = this.parceUser(decodedJwt);
  }

  private parceUser(jwt : any) : UserInfo{
    return {
      id : jwt.jti,
      role : jwt.authorities[0]
    }
  }

  private parseJwt(token : string) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
  }

  private isTokenExperd(decodedJwt : any) : boolean{
    return decodedJwt.exp >= new Date().getTime();
  }
}