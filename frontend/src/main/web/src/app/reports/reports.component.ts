import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';
import {any} from 'codelyzer/util/function';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Transaction} from '../model/transaction';

@Component({
    selector: 'app-reports',
    templateUrl: './reports.component.html',
    styles: ['table { width: 100%; }']
})
export class ReportsComponent implements OnInit {

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }),
    };

    public reportsForm: FormGroup;

    displayedColumns: string[] = ['parkingId', 'arrivalTime', 'departureTime', 'duration', 'cost', 'userId'];
    dataSource = new MatTableDataSource<Transaction>();

    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    parkingSpaces: ParkingSpace[];
    revenue: number;
    distinctUsers: number;
    sessionsNumber: number;

    constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

    ngOnInit() {
        /* Fetching logged user. Can be replaced with authentication service */
        this.http.get<User>('/auth/logged', this.httpOptions).subscribe(user => {

            /* Fetching parking spaces for current admin */
            this.http.get<ParkingSpace[]>('/api/parking/space/admin/' + user.id, this.httpOptions).subscribe(parkings => {

                this.parkingSpaces = parkings;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                } else {
                    console.log(error);
                    this.notification.show('warning', 'Failed fetching parking spaces', 'top', 'center');
                }
            });

        }, error => {
            this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
            this.router.navigate(['/login']);
        });


        this.reportsForm = new FormGroup({
            parkingId: new FormControl('', [Validators.required, Validators.maxLength(255)]),
            startDate: new FormControl('', [Validators.required]),
            endDate: new FormControl('', [Validators.required])
        });
        const lastMonthDate = new Date();
        lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
        this.reportsForm.controls['startDate'].setValue(lastMonthDate, {onlySelf: true});
        this.reportsForm.controls['endDate'].setValue(new Date(), {onlySelf: true});

        this.dataSource.paginator = this.paginator;
    }

    public hasError = (controlName: string, errorName: string) => {
        return this.reportsForm.controls[controlName].hasError(errorName);
    };

    public getTransactions = (reportsFormValue) => {
        if (this.reportsForm.valid) {
            const newReportsRequest = {
                parkingId: reportsFormValue.parkingId,
                startDate: reportsFormValue.startDate,
                endDate: reportsFormValue.endDate
            };

            this.http.post<Transaction[]>('/api/reports/transactions', newReportsRequest, this.httpOptions ).subscribe(transactions => {
                this.dataSource.data = transactions;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });

            this.http.post<number>('/api/reports/revenue', newReportsRequest, this.httpOptions).subscribe(revenue => {
                this.revenue = revenue;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });

            this.http.post<number>('/api/reports/distinct/users', newReportsRequest, this.httpOptions).subscribe(distinctUsers => {
                this.distinctUsers = distinctUsers;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });

            this.http.post<number>('/api/reports/sessions', newReportsRequest, this.httpOptions).subscribe(sessionsNumber => {
                this.sessionsNumber = sessionsNumber;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });
        }
    };

    public getRevenue = (reportsFormValue) => {
        if (this.reportsForm.valid) {
            const newReportsRequest = {
                parkingId: reportsFormValue.parkingId,
                startDate: reportsFormValue.startDate,
                endDate: reportsFormValue.endDate
            };

            this.http.post<number>('/api/reports/revenue', newReportsRequest, this.httpOptions).subscribe(revenue => {
                this.revenue = revenue;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });
        }
    };

    public getDistinctUsers = (reportsFormValue) => {
        if (this.reportsForm.valid) {
            const newReportsRequest = {
                parkingId: reportsFormValue.parkingId,
                startDate: reportsFormValue.startDate,
                endDate: reportsFormValue.endDate
            };

            this.http.post<number>('/api/reports/distinct/users', newReportsRequest, this.httpOptions).subscribe(distinctUsers => {
                this.distinctUsers = distinctUsers;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });
        }
    };

    public getSessionsNumber = (reportsFormValue) => {
        if (this.reportsForm.valid) {
            const newReportsRequest = {
                parkingId: reportsFormValue.parkingId,
                startDate: reportsFormValue.startDate,
                endDate: reportsFormValue.endDate
            };

            this.http.post<number>('/api/reports/sessions', newReportsRequest, this.httpOptions).subscribe(sessionsNumber => {
                this.sessionsNumber = sessionsNumber;
            }, error => {
                if (error.status === 403) {
                    this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
                    this.router.navigate(['/login']);
                }
            });
        }
    };
}
