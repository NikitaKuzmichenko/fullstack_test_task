import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { ContentPage } from "../entity/page";

const jsonHeader = new HttpHeaders({
    'Content-Type': 'application/json',
  })
  
@Injectable({
    providedIn: 'root'
  })
export class SensorTypeServie{

    public readonly API_URL : string = "http://localhost:8080/Test/v1/sensorTypes";

    constructor(private http: HttpClient){}

    getAll(): Observable<ContentPage> {
        return this.http.get<ContentPage>(this.API_URL,{ headers: jsonHeader});
    }
}