import { Routes } from '@angular/router';

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

export const AdminLayoutRoutes: Routes = [
    { path: 'login',             component: LoginComponent },
    { path: 'reset-password',    component: ResetPasswordComponent },
    { path: 'add-parking',       component: AddParkingComponent },
    { path: 'parking-spaces',    component: ParkingSpacesComponent },
    { path: 'find-parking',      component: FindParkingComponent },
    { path: 'current-session',   component: CurrentSessionComponent },
    { path: 'session-history',   component: SessionHistoryComponent },
    { path: 'reports',           component: ReportsComponent },
    { path: 'add-car',           component: AddCarComponent},
    { path: 'register',          component: RegisterComponent},
];
