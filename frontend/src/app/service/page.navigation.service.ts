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
        this.mapper.set(Page.EditSensorPage, this.BASE_URL + "/sensors/edit");
        this.mapper.set(Page.SesorsListPage, this.BASE_URL + "/sensors");
    }

    public getPath(page: Page): string {
        let result = this.mapper.get(page)
        if (result === undefined) {
            result = this.BASE_URL;
        }
        return result;
    }

    public goToPage(page: Page): void {
        this.router.navigateByUrl(this.getPath(page));
    }
}