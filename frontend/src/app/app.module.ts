import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule,Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './component/page/login/login/login.component';
import { SensorTableComponent } from './component/page/sensorTable/sensor-table/sensor-table.component';
import { SensorEditorComponent } from './component/page/sensorEditor/sensor-editor/sensor-editor.component';

const appRoutes: Routes =[
  {
    path :'login' ,
    component : LoginComponent
  },
  { 
    path: '', 
    redirectTo: '/login', 
    pathMatch: 'full' 
  },
  {
    path :'sensors/create' ,
    component : SensorEditorComponent
  },
  {
    path :'sensors/edit' ,
    component : SensorEditorComponent
  },
  {
    path :'sensors' ,
    component : SensorTableComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SensorTableComponent,
    SensorEditorComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
