import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Sensor } from 'src/app/entity/sensor';
import { AuthService } from 'src/app/service/auth/auth.service';
import { Page, PageNavigator } from 'src/app/service/page.navigation.service';
import { SensorServie } from 'src/app/service/sesor.service';

@Component({
  selector: 'app-sensor-table',
  templateUrl: './sensor-table.component.html',
  styleUrls: ['./sensor-table.component.scss']
})
export class SensorTableComponent implements OnInit {

  totalSensorsAmount : number = 0;
  sensors : Sensor[] = []

  nearestPageNumbers : number[] = [];
  currentPageNumber : number = 1;

  limit : number = 4;
  offset : number = 0;
  fuildFilter : string | undefined = undefined;

  search_form = new FormGroup({
    filter: new FormControl('',[Validators.required,Validators.minLength(1)])
  })

  constructor(private sensorServie : SensorServie,private authService : AuthService, private pageNavigator : PageNavigator) { 
    this.loadData();
  }

  ngOnInit(): void {}

  onDelete(id : number) :void{
    this.loadData();
  }
  
  createSensor(): void{
    this.pageNavigator.goToPage(Page.CreateSensorPage);
  }

  logOut(): void{
    this.authService.logOut();
    this.pageNavigator.goToPage(Page.LoginPage);
  }

  search(): void{
    if(this.search_form.valid){
      this.fuildFilter =this.search_form.get("filter")?.value!;
    }
    else{
      this.fuildFilter = undefined;
    }
    this.loadData();
  }

  previousPage(): void{
    if(this.currentPageNumber > 1){
      this.currentPageNumber--;
      this.offset -= this.limit;
      if(this.offset < 0){
        this.offset = 0;
      }
      this.loadData();
    }
  }

  nextPage(): void{
    if(this.currentPageNumber < Math.ceil(this.totalSensorsAmount / this.limit)){
      this.currentPageNumber++;
      this.offset += this.limit;
      this.loadData();
    }
  }

  private loadData(): void{
    this.sensorServie.getAll(this.limit,this.offset,this.fuildFilter).subscribe({
      next: (result) =>{
          this.sensors = result.content;
          this.totalSensorsAmount = result.totalEntitiesAmount;
          this.calculatepagination()
      }
  })
  }

  private calculatepagination(): void{
    this.nearestPageNumbers = [];

    let totalPagesAmount = Math.ceil(this.totalSensorsAmount / this.limit);
    if(totalPagesAmount <= 3){
      for(let i = 1; i <= totalPagesAmount; i++){
        this.nearestPageNumbers.push(i);
      }
      return;
    }
    
    if(this.currentPageNumber <= 2){
      for(let i = 1; i <= 3;i++){
        this.nearestPageNumbers.push(i);
      }
      this.nearestPageNumbers.push(-1);
      this.nearestPageNumbers.push(totalPagesAmount);
      return;
    }

    if(totalPagesAmount - this.currentPageNumber < 2){
      this.nearestPageNumbers.push(1);
      this.nearestPageNumbers.push(-1);
      for(let i = 2; i >= 0 ;i--){
        this.nearestPageNumbers.push(totalPagesAmount - i);
      }
      return;
    }

    this.nearestPageNumbers.push(1);
    this.nearestPageNumbers.push(-1);

    this.nearestPageNumbers.push(this.currentPageNumber-1);
    this.nearestPageNumbers.push(this.currentPageNumber);
    this.nearestPageNumbers.push(+this.currentPageNumber + +1);

    this.nearestPageNumbers.push(-1);
    this.nearestPageNumbers.push(totalPagesAmount);
  }

  goToPage(event : any) : void{
    let value = event.target.value;
    console.log(value);
    
    if(value < 1){
      return;
    }
    
    this.currentPageNumber = value;
    this.offset = this.limit * (this.currentPageNumber - 1);
    this.loadData();
  }
}
