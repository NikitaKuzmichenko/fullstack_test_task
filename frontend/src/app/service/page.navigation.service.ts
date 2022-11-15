import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

export enum Page {
    SesorsListPage,
    LoginPage,
    CreateSensorPage,
    EditSensorPage,
}

@Injectable({
    providedIn: 'root'
  })
export class PageNavigator{

    private readonly BASE_URL: string = "";
    private mapper: Map<Page,string>;

    constructor(private router : Router){
        this.mapper = new Map<Page, string>();

        this.mapper.set(Page.LoginPage, this.BASE_URL + "/login");
        this.mapper.set(Page.CreateSensorPage, this.BASE_URL + "/sensors/create");
        this.mapper.set(Page.EditSensorPage, this.BASE_URL + "/sensors/{0}/edit");
        this.mapper.set(Page.SesorsListPage, this.BASE_URL + "/sensors");
    }

    public goToPage(page: Page, pathParams?: string[] | undefined): void {
        this.router.navigateByUrl(this.getPath(page,pathParams));
    }

    public getPath(page: Page, pathParams?: string[] | undefined): string {
        let result = this.mapper.get(page)
        if (result === undefined) {
            result = this.BASE_URL;
        }
        else {
            if (pathParams !== undefined) {
               result = formatString(result,pathParams);
            }
        }
        return result;
    }
}

export function formatString(path : string,args : string[]) : string{
    var formatted = path;
    for (var i = 0; i < args.length; i++) {
        var regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, args[i]);
    }
    return formatted;
}
