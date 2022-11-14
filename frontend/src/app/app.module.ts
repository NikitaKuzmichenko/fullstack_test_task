import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule,Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'; 

import { AppComponent } from './app.component';
import { LoginComponent } from './component/page/login/login/login.component';
import { SensorTableComponent } from './component/page/sensorTable/sensor-table/sensor-table.component';
import { SensorEditorComponent } from './component/page/sensorEditor/sensor-editor/sensor-editor.component';
import { AuthInterceptor } from './service/auth/auth.Interceptor';
import { AdminAccessGuard } from './service/guard/admin.access.guard';
import { UserAccessGuard } from './service/guard/user.access.guard';

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
    component : SensorEditorComponent,
    canActivate: [AdminAccessGuard]
  },
  {
    path :'sensors/edit' ,
    component : SensorEditorComponent,
    canActivate: [AdminAccessGuard]
  },
  {
    path :'sensors' ,
    component : SensorTableComponent,
    canActivate: [UserAccessGuard]
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
    BrowserModule,
    HttpClientModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },UserAccessGuard,AdminAccessGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
