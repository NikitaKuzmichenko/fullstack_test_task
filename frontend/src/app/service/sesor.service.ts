import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { of } from "rxjs/internal/observable/of";
import { catchError } from "rxjs/internal/operators/catchError";
import { map } from "rxjs/internal/operators/map";
import { Sensor } from "../entity/sensor";

const jsonHeader = new HttpHeaders({
    'Content-Type': 'application/json',
  })
  
@Injectable({
    providedIn: 'root'
  })
export class SensorServie{

    private readonly API_URL : string = "http://localhost:8080/Test/v1/sensors";

    constructor(private http: HttpClient){}

    getAll(): Observable<Sensor[]> {
        return this.http.get<Sensor[]>(this.API_URL);
    }

    getById(id : number): Observable<Sensor[]> {
      console.log("retrive data");
      
        return this.http.get<Sensor[]>(this.API_URL);
    }

    create(sensor : Sensor): Observable<boolean> {
        return this.http.post<Sensor>(this.API_URL,sensor,{ headers: jsonHeader}).pipe(
            map(e=>true),
            catchError(error => {
              return of(false);
            }));
    }

    update(sensor : Sensor,id : number): Observable<boolean> {
        return this.http.post<Sensor>(this.API_URL + '/' + id,sensor,{ headers: jsonHeader}).pipe(
            map(e=>true),
            catchError(error => {
              return of(false);
            }));
    }
}