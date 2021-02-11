import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { AdminLayoutRoutes } from './admin-layout.routing';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatRippleModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

import { LoginComponent } from '../../login/login.component';
import { ResetPasswordComponent } from '../../reset-password/reset-password.component';

import { ParkingSpacesComponent } from '../../parking-spaces/parking-spaces.component';
import { AddParkingComponent } from '../../add-parking/add-parking.component';
import { FindParkingComponent } from '../../find-parking/find-parking.component';
import { CurrentSessionComponent } from '../../current-session/current-session.component';
import { SessionHistoryComponent } from '../../session-history/session-history.component';
import { ReportsComponent } from '../../reports/reports.component';
import {AddCarComponent} from '../../add-car/add-car.component';
import {RegisterComponent} from '../../register/register.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatGridListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  declarations: [
    LoginComponent,
    ResetPasswordComponent,
    ParkingSpacesComponent,
    AddParkingComponent,
    FindParkingComponent,
    CurrentSessionComponent,
    SessionHistoryComponent,
    ReportsComponent,
    AddCarComponent,
    RegisterComponent,
  ]
})

export class AdminLayoutModule {}
