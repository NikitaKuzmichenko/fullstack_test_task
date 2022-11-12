import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './component/page/login/login/login.component';
import { SensorTableComponent } from './component/page/sensorTable/sensor-table/sensor-table.component';
import { SensorEditorComponent } from './component/page/sensorEditor/sensor-editor/sensor-editor.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SensorTableComponent,
    SensorEditorComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
