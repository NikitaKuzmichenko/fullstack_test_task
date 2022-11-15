import { Injectable } from "@angular/core";

export enum ErrorMsgName {
    LoginFormNotFilled,
    AuthorizationError,
    AuthentificationError,
    SensorFormNotFilled,
    ImpossibleRangeValues,
    DefaultError
}

@Injectable({
    providedIn: 'root'
  })
export class ErrorMsgProvider{
    private errorMap : Map<ErrorMsgName,string> ;

    constructor(){
        this.errorMap = new Map<ErrorMsgName,string>();
        this.errorMap.set(ErrorMsgName.LoginFormNotFilled,"Login form not fuild");
        this.errorMap.set(ErrorMsgName.AuthorizationError,"You don't hava accsess to requested data");
        this.errorMap.set(ErrorMsgName.AuthentificationError,"Account with given credentials doesn't exit");
        this.errorMap.set(ErrorMsgName.SensorFormNotFilled,"You must fill all required fields in this from");
        this.errorMap.set(ErrorMsgName.ImpossibleRangeValues,"FROM value cannot be greater then To value");
        this.errorMap.set(ErrorMsgName.DefaultError,"Unknown error occurred");
    }

    public getErrorByName(errorName : ErrorMsgName) : string{
        if(!this.errorMap.has(errorName)){
            errorName = ErrorMsgName.DefaultError; 
        }
        let result = this.errorMap.get(errorName)
        if(result === undefined){
            return "";
        }
        return result;
    }
}