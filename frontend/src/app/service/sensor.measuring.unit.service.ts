import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { ContentPage } from "../entity/page";

@Injectable({
    providedIn: 'root'
  })
export class MeasuringUnitServie{

    public readonly API_URL : string = "http://localhost:8080/Test/v1/measurementsUnits";

    constructor(private http: HttpClient){}

    getAll(): Observable<ContentPage> {
        return this.http.get<ContentPage>(this.API_URL);
    }
}