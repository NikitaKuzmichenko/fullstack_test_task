import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { of } from "rxjs/internal/observable/of";
import { catchError } from "rxjs/internal/operators/catchError";
import { map } from "rxjs/internal/operators/map";
import { ContentPage } from "../entity/page";
import { Sensor } from "../entity/sensor";

const jsonHeader = new HttpHeaders({
    'Content-Type': 'application/json',
  })
  
@Injectable({
    providedIn: 'root'
  })
export class SensorServie{

    private readonly API_URL : string = "http://localhost:8080/Test/v1/sensors";

    private readonly LIMIT_PARAM_NAME : string = "limit";
    private readonly OFFSET_PARAM_NAME : string = "offset";
    private readonly FILTER_PARAM_NAME : string = "fieldFilter";

    constructor(private http: HttpClient){}

    getAll(limit? : number, offset? : number, fuildFilter? : string): Observable<ContentPage> {
      let queryParams = new HttpParams();
      if(limit != undefined){
        queryParams = queryParams.append(this.LIMIT_PARAM_NAME,limit);
      }
      if(offset != undefined){
        queryParams = queryParams.append(this.OFFSET_PARAM_NAME,offset);
      }
      if(fuildFilter != undefined){
        queryParams = queryParams.append(this.FILTER_PARAM_NAME,fuildFilter);
      }
      return this.http.get<ContentPage>(this.API_URL,{params: queryParams});
    }

    getById(id : number): Observable<Sensor> {
        return this.http.get<Sensor>(this.API_URL + '/' + id);
    }

    create(sensor : Sensor): Observable<boolean> {
        return this.http.post<Sensor>(this.API_URL,sensor,{ headers: jsonHeader}).pipe(
            map(e=>true),
            catchError(error => {
              return of(false);
            }));
    }

    update(sensor : Sensor,id : number): Observable<boolean> {
        return this.http.put<Sensor>(this.API_URL + '/' + id,sensor,{ headers: jsonHeader}).pipe(
            map(e=>true),
            catchError(error => {
              return of(false);
            }));
    }

    delete(id : number) : Observable<boolean>{
      return this.http.delete<any>(this.API_URL + '/' + id).pipe(
        map(e=>true),
        catchError(error => {
          return of(false);
        }));
    }
}