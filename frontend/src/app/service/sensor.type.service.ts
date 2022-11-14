import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { SensorType } from "../entity/sensor.type";

const jsonHeader = new HttpHeaders({
    'Content-Type': 'application/json',
  })
  
@Injectable({
    providedIn: 'root'
  })
export class SensorTypeServie{

    public readonly API_URL : string = "http://localhost:8080/Test/v1/sensorTypes";

    constructor(private http: HttpClient){}

    getAll(): Observable<SensorType[]> {
        return this.http.get<SensorType[]>(this.API_URL,{ headers: jsonHeader});
    }
}