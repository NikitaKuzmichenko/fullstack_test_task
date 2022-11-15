import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Sensor } from 'src/app/entity/sensor';
import { AuthService } from 'src/app/service/auth/auth.service';
import { Page, PageNavigator } from 'src/app/service/page.navigation.service';
import { SensorServie } from 'src/app/service/sesor.service';

@Component({
  selector: 'app-sensor-preview',
  templateUrl: './sensor-preview.component.html',
  styleUrls: ['./sensor-preview.component.scss']
})
export class SensorPreviewComponent implements OnInit {

  range : string = "";

  buttonsDisabled : boolean = false;

  roleWithEditPermissions : string = "Administrator";

  @Output() deleted : EventEmitter<number> = new EventEmitter();

  @Input("sensor") sensor! : Sensor;
  
  constructor(private authService : AuthService, private pageNavigator : PageNavigator,private sensorServie : SensorServie) {}

  ngOnInit() : void {
    this.range = this.sensor.lowerOperationalBound + " - " + this.sensor.higherOperationalBound;
    this.buttonsDisabled = !(this.authService.getCurrentUser()?.role == this.roleWithEditPermissions);
  }

  editSensor() : void{
    this.pageNavigator.goToPage(Page.EditSensorPage,[String(this.sensor.id)]);
  }

  deleteSensor() : void{
    this.sensorServie.delete(this.sensor.id).subscribe({
      next : (result) =>{
        this.deleted.emit();
      }
    }
    )
    console.log("delete");
  }
}
