import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule,Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'; 

import { AppComponent } from './app.component';
import { LoginComponent } from './component/page/login/login/login.component';
import { SensorTableComponent } from './component/page/sensorTable/sensor-table/sensor-table.component';
import { SensorEditorComponent } from './component/page/sesnor/sensor-editor/sensor-editor.component';
import { AuthInterceptor } from './service/auth/auth.Interceptor';
import { AdminAccessGuard } from './service/guard/admin.access.guard';
import { UserAccessGuard } from './service/guard/user.access.guard';
import { ReactiveFormsModule } from '@angular/forms';
import { SensorCreatorComponent } from './component/page/sesnor/sensor-creator/sensor-creator.component';
import { SensorPreviewComponent } from './component/page/sensorTable/sensor-preview/sensor-preview.component';

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
    component : SensorCreatorComponent,
    canActivate: [AdminAccessGuard]
  },
  {
    path :'sensors/:id/edit' ,
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
    SensorEditorComponent,
    SensorCreatorComponent,
    SensorPreviewComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule 
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },UserAccessGuard,AdminAccessGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
