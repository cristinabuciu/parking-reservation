import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';
import {RegisteredCar} from "../model/registered-car";

@Component({
    selector: 'app-add-car',
    templateUrl: './add-car.component.html',
    styles: ['']
})
export class AddCarComponent implements OnInit {

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }),
    };

    public addCarForm: FormGroup;


    constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

    ngOnInit() {
        this.addCarForm = new FormGroup({
            carPlate: new FormControl('', [Validators.required, Validators.maxLength(255)]),
        });
    }

    public hasError = (controlName: string, errorName: string) => {
        if (this.addCarForm.controls[controlName].hasError(errorName) === true) {
            var x = controlName.split('');
            if (x.length > 8) {
                return false
            } else if (x.length === 8 && (!this.isLetter(x[0]) || !this.isLetter(x[1]) || !this.isLetter(x[5]) || !this.isLetter(x[6]) || !this.isLetter(x[7]))) { return false; }
            else if (!this.isDigit(x[0]) || !this.isDigit(x[1]) || !this.isDigit(x[5])) { return false}
        }
        return this.addCarForm.controls[controlName].hasError(errorName);
    };

    public isLetter = (character) => {
        return (character > 'A' && character < 'Z');
    };

    public isDigit = (character) => {
        return (character > '0' && character < '9');
    };

    public registerCar = (addCarFormValue) => {
        if (this.addCarForm.valid) {
            const newCarRegistered = {
                id: null,
                userId: null,
                licencePlate: addCarFormValue.carPlate
            };
        this.http.post<RegisteredCar>('/api/registered/cars', newCarRegistered, this.httpOptions).subscribe(parkingSpace => {
            this.notification.show('success', 'Successfully registered car', 'top','center');
        }, error => {
            if (error.status === 403) {
                this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                this.router.navigate(['/login']);
            } else {
                console.log(error);
                this.notification.show('warning', 'Failed fetching parking spaces', 'top', 'center');
            }
        });
        }
    };
}
