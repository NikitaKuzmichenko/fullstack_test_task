import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from "rxjs"
import { AuthService } from "./auth.service"

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    private ignoreUrl : string[] = [];

    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    constructor(private authService: AuthService,) {
        this.ignoreUrl.push(authService.REFRESH_TOKEN_URL);
    }

    intercept(req: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {
        if(this.ignoreUrl.includes(req.url)){
            return next.handle(req);
        }

        let token = this.authService.getJwtToken();
        let authReq = req;
        if(token != null){
            authReq = this.addToken(req, token);
        }

        return next.handle(authReq).pipe(
            catchError(error => {    
            if (error instanceof HttpErrorResponse && error.status === 401) {
                return this.handle401Error(authReq , next);
            } else {
                return throwError(()=>error);
            }
        }));
    }

    private addToken(req: HttpRequest<any>, token: string) {
        return req.clone({
            headers: req.headers.set(this.authService.JWT_TOKEN, token)
        });
    }

    private handle401Error(request: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
        
        if (!this.isRefreshing) {
          this.isRefreshing = true;
          this.refreshTokenSubject.next(false);
    
          return this.authService.refreshToken().pipe(
            switchMap((tokenUpdated : boolean) => {
              this.isRefreshing = false;
              this.refreshTokenSubject.next(tokenUpdated);
              let t = this.authService.getJwtToken();
              return next.handle(this.addToken(request, this.authService.getJwtToken()!));
            }));
    
        } else {
          return this.refreshTokenSubject.pipe(
            filter(token => token != false),
            take(1),
            switchMap(jwt => {
                return next.handle(this.addToken(request, this.authService.getJwtToken()!));
            }));
        }
    }
}