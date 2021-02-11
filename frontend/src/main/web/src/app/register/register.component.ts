import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styles: ['']
})
export class RegisterComponent implements OnInit {

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }),
    };

    public registerForm: FormGroup;

    roles: any[] = [
        {value: 'ADMIN', viewValue: 'Parking owner'},
        {value: 'USER', viewValue: 'Normal user'},
    ];
    roleDefault = 'USER';

    constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

    ngOnInit() {
        this.registerForm = new FormGroup({
            name: new FormControl('', [Validators.required, Validators.maxLength(255)]),
            username: new FormControl('', [Validators.required, Validators.maxLength(255)]),
            password: new FormControl('', [Validators.required]),
            email: new FormControl('', [Validators.required]),
            role: new FormControl('', [Validators.required])
        });
        this.registerForm.controls['role'].setValue(this.roleDefault, {onlySelf: true});
    }

    public hasError = (controlName: string, errorName: string) =>{
        return this.registerForm.controls[controlName].hasError(errorName);
    };

    public registerUser = (registerFormValue) => {
        if (this.registerForm.valid) {
            const newUser = {
                id: null,
                username: registerFormValue.username,
                password: registerFormValue.password,
                role: registerFormValue.role,
                enabled:  true,
                name: registerFormValue.name,
                email: registerFormValue.email
            };
            this.http.post<User>('/auth/user/create', newUser, this.httpOptions).subscribe(parkingSpace => {
                console.log(newUser);
                this.notification.show('success', 'Successfully created parking space', 'top','center');
                this.router.navigate(['/login']);
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
