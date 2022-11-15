import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Sensor } from 'src/app/entity/sensor';
import { MeasuringUnit } from 'src/app/entity/sensor.measuring.unit';
import { SensorType } from 'src/app/entity/sensor.type';
import { Page, PageNavigator } from 'src/app/service/page.navigation.service';
import { MeasuringUnitServie } from 'src/app/service/sensor.measuring.unit.service';
import { SensorTypeServie } from 'src/app/service/sensor.type.service';
import { SensorServie } from 'src/app/service/sesor.service';
import { ErrorMsgName, ErrorMsgProvider } from 'src/app/utils/error.msg.provider';

@Component({
  selector: 'app-sensor-creator',
  templateUrl: './sensor-creator.component.html',
  styleUrls: ['.././sensor-creator.component.scss']
})
export class SensorCreatorComponent implements OnInit {
    rangeFrom : number = 0;
    rangeTo : number = 0;
  
    isRangeChanged : boolean = false;
  
    selectedSensorTypeId : number = 0;
    selectedSensorMUId : number = 0;
  
    sensorTypes : SensorType[] = [];
    measuringUnits : MeasuringUnit[] = []
  
    errorMsg: string = "";
    errorPresent : boolean = false;
  
    sensor_form = new FormGroup({
      name: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(30)]),
      model: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(15)]),
      rangeFrom: new FormControl(this.rangeFrom),
      rangeTo: new FormControl(this.rangeTo),
      location: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(40)]),
      description: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(200)]),
    })
  
    constructor(protected route: ActivatedRoute, protected errorMsgProvider : ErrorMsgProvider, 
      protected sensorServie : SensorServie, protected sensorTypeServie : SensorTypeServie,
      protected measuringUnitServie : MeasuringUnitServie,protected pageNavigator : PageNavigator) { 
  
      measuringUnitServie.getAll().subscribe({
        next: (units) =>{
          this.measuringUnits = units.content;
        }
      })

      sensorTypeServie.getAll().subscribe({
        next: (types) =>{
          this.sensorTypes = types.content;
        }
      })
    }
  
    onMUChange(change: any) : void{
      this.selectedSensorMUId = change.target.value;
    }
  
    onTypeChange(change: any) : void{
      this.selectedSensorTypeId = change.target.value;
    }
  
    onSave(){
      this.errorPresent = false;
  
      if(!this.sensor_form.valid){
        this.errorMsg = this.errorMsgProvider.getErrorByName(ErrorMsgName.SensorFormNotFilled);
        this.errorPresent = true;
        return;
      }
  
      if(this.isRangeChanged && (this.rangeFrom - this.rangeTo > 0)){
        this.errorMsg = this.errorMsgProvider.getErrorByName(ErrorMsgName.ImpossibleRangeValues);
        this.errorPresent = true;
        return;
      }
  
      let currentSensor : Sensor = {
        id : 0,
        name : this.sensor_form.get("name")?.value!,
        model : this.sensor_form.get("model")?.value!,
        lowerOperationalBound : this.rangeFrom,
        higherOperationalBound : this.rangeTo,
        measurementsUnit : this.findMUById(this.selectedSensorMUId),
        type : this.findSensoTypeById(this.selectedSensorTypeId),
        location : this.sensor_form.get("location")?.value!,
        description : this.sensor_form.get("description")?.value!
      }
      
      this.sensorServie.create(currentSensor).subscribe({
        next: (e) => {
          this.pageNavigator.goToPage(Page.SesorsListPage);
        },
        error: (e) => {
          this.errorMsg = this.errorMsgProvider.getErrorByName(ErrorMsgName.DefaultError);
          this.errorPresent = true;
        }
      });
    }
  
    onCancele(){
      this.pageNavigator.goToPage(Page.SesorsListPage);
    }
  
    incFrom(){
      this.rangeFrom++;
      this.sensor_form.get("rangeFrom")?.setValue(this.rangeFrom);
      this.isRangeChanged = true;
    }
  
    incTo(){
      this.rangeTo++;
      this.sensor_form.get("rangeTo")?.setValue(this.rangeTo);
      this.isRangeChanged = true;
    }
  
    decFrom(){
      this.rangeFrom--;
      this.sensor_form.get("rangeFrom")?.setValue(this.rangeFrom);
      this.isRangeChanged = true;
    }
  
    decTo(){
      this.rangeTo--;
      this.sensor_form.get("rangeTo")?.setValue(this.rangeTo);
      this.isRangeChanged = true;
    }
  
    fromChange(change : any){
      this.rangeFrom = change.target.value;
      this.isRangeChanged = true;
    }
  
    toChange(change : any){
      this.rangeTo = change.target.value;
      this.isRangeChanged = true;
    }
  
    findMUById(id : number) : MeasuringUnit{
      return this.measuringUnits.find(e => e.id == id)!;
    }
  
    findSensoTypeById(id : number) : SensorType{
      return this.sensorTypes.find(e => e.id == id)!;
    }
  
    ngOnInit(): void {}
}
