import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { Transaction } from '../model/transaction';
import { User } from '../model/user';

@Component({
  selector: 'app-session-history',
  templateUrl: './session-history.component.html',
  styles: ['table { width: 100%; }']
})
export class SessionHistoryComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  parkingSpacesMap = {};

  public startDate: FormControl;
  public endDate: FormControl;
  public userTransactionsForm: FormGroup;

  displayedColumns: string[] = ['parkingId', 'arrivalTime', 'departureTime', 'duration', 'cost'];
  // displayedColumns: string[] = ['arrivalTime', 'departureTime', 'duration', 'cost'];
  dataSource = new MatTableDataSource<Transaction>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

  ngOnInit() {
    this.startDate = new FormControl('', [Validators.required]);
    this.endDate = new FormControl('', [Validators.required]);
    this.userTransactionsForm = new FormGroup({
      startDate: this.startDate,
      endDate: this.endDate
    });
    const lastMonthDate = new Date();
    lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
    this.userTransactionsForm.controls['startDate'].setValue(lastMonthDate, {onlySelf: true});
    this.userTransactionsForm.controls['endDate'].setValue(new Date(), {onlySelf: true});

    this.dataSource.paginator = this.paginator;

    /* Fetching logged user. Can be replaced with authentication service */
    this.http.get<User>("/auth/logged", this.httpOptions).subscribe(user => {

      /* Fetching parking spaces for current user */
      this.http.get<ParkingSpace[]>("/api/parking/space/" , this.httpOptions).subscribe(parkingSpaces => {
        this.parkingSpacesMap = parkingSpaces.reduce(function(map, obj) {
          map[obj.id] = obj;
          return map;
        }, {});
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
  }

  public requestUserTransactions = (userTransactionsFormValue) => {

    this.dataSource.data = [];

    /* Fetching transactions for current user */
    this.http.post<Transaction[]>("/api/transactions/user", userTransactionsFormValue, this.httpOptions).subscribe(transactions => {
      this.dataSource.data = transactions;
      console.log(transactions)
    }, error => {
      if (error.status === 403) {
        this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
        this.router.navigate(['/login']);
      }
    });
  }

}
