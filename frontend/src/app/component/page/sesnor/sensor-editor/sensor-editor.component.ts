import { Sensor } from '../../../../entity/sensor';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ErrorMsgName, ErrorMsgProvider } from 'src/app/utils/error.msg.provider';
import { Page, PageNavigator } from 'src/app/service/page.navigation.service';
import { SensorServie } from '../../../../service/sesor.service';
import { SensorTypeServie } from '../../../../service/sensor.type.service';
import { MeasuringUnitServie } from '../../../../service/sensor.measuring.unit.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SensorType } from 'src/app/entity/sensor.type';
import { MeasuringUnit } from 'src/app/entity/sensor.measuring.unit';

@Component({
  selector: 'app-sensor-editor',
  templateUrl: './sensor-editor.component.html',
  styleUrls: ['.././sensor-creator.component.scss']
})
export class SensorEditorComponent implements OnInit {

  loadedSensor! : Sensor;

  rangeFrom : number = 0;
  rangeTo : number = 0;

  isRangeChanged : boolean = false;

  selectedSensorTypeId : number = -1;
  selectedSensorMUId : number = -1;

  sensorTypes : SensorType[] = [];
  measuringUnits : MeasuringUnit[] = []

  errorMsg: string = "";
  errorPresent : boolean = false;

  update_sensor_form = new FormGroup({
    name: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(30)]),
    model: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(15)]),
    rangeFrom: new FormControl(this.rangeFrom),
    rangeTo: new FormControl(this.rangeTo),
    location: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(40)]),
    description: new FormControl('',[Validators.required,Validators.minLength(1),Validators.maxLength(200)]),
  })

  constructor(private route: ActivatedRoute, private errorMsgProvider : ErrorMsgProvider, 
    private sensorServie : SensorServie, private sensorTypeServie : SensorTypeServie,
    private measuringUnitServie : MeasuringUnitServie,private pageNavigator : PageNavigator) { 

    let idParam = this.route.snapshot.paramMap.get('id');
    if (idParam === null) {
      return;
    }

    sensorServie.getById(Number(idParam)).subscribe({
      next: (sensor) => {
        this.loadedSensor = sensor;
        
        this.update_sensor_form.setValue({
          name:sensor.name,
          model:sensor.model,
          rangeFrom:sensor.lowerOperationalBound,
          rangeTo:sensor.higherOperationalBound,
          location:sensor.location,
          description:sensor.description,
        })

        this.rangeFrom = sensor.lowerOperationalBound;
        this.rangeTo = sensor.higherOperationalBound;

        this.selectedSensorTypeId = sensor.type.id;
        this.selectedSensorMUId = sensor.measurementsUnit.id;

        this.measuringUnitServie.getAll().subscribe({
          next: (units) =>{
            this.measuringUnits = units.content;
          }
        })
    
        this.sensorTypeServie.getAll().subscribe({
          next: (types) =>{
            this.sensorTypes = types.content;
          }
        })
      }
    })
  }

  ngOnInit(): void {}

  onMUChange(change: any) : void{
    this.selectedSensorMUId = change.target.value - 1;
  }

  onTypeChange(change: any) : void{
    this.selectedSensorTypeId = change.target.value - 1;
  }

  onSave(){

    this.errorPresent = false;

    if(!this.update_sensor_form.valid){
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
      id : this.loadedSensor.id,
      name : this.update_sensor_form.get("name")?.value!,
      model : this.update_sensor_form.get("model")?.value!,
      lowerOperationalBound : this.rangeFrom,
      higherOperationalBound : this.rangeTo,
      measurementsUnit : this.findMUById(this.selectedSensorMUId),
      type : this.findSensoTypeById(this.selectedSensorTypeId),
      location : this.update_sensor_form.get("location")?.value!,
      description : this.update_sensor_form.get("description")?.value!
    }
    
    this.sensorServie.update(currentSensor,this.loadedSensor.id).subscribe({
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
    this.update_sensor_form.get("rangeFrom")?.setValue(this.rangeFrom);
    this.isRangeChanged = true;
  }

  incTo(){
    this.rangeTo++;
    this.update_sensor_form.get("rangeTo")?.setValue(this.rangeTo);
    this.isRangeChanged = true;
  }

  decFrom(){
    this.rangeFrom--;
    this.update_sensor_form.get("rangeFrom")?.setValue(this.rangeFrom);
    this.isRangeChanged = true;
  }

  decTo(){
    this.rangeTo--;
    this.update_sensor_form.get("rangeTo")?.setValue(this.rangeTo);
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

  private findMUById(id : number) : MeasuringUnit{
    return this.measuringUnits.find(e => e.id === id)!;
  }

  private findSensoTypeById(id : number) : SensorType{
    return this.sensorTypes.find(e => e.id === id)!;
  }

}
