import {Component, Inject, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { NotificationService } from '../service/notification.service';

import { OpenSession } from '../model/open-session';
import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {Transaction} from "../model/transaction";


@Component({
  selector: 'app-current-session',
  templateUrl: './current-session.component.html',
  styles: ['']
})
export class CurrentSessionComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  parkingSpace: ParkingSpace;

  openSession: OpenSession;
  requestedOpenSession: boolean = false;
  currentSumary: Transaction;

  constructor(private http: HttpClient, private router: Router, private notification: NotificationService, public dialog: MatDialog) { }

  ngOnInit() {
    /* Fetching logged user. Can be replaced with authentication service */
    this.http.get<User>("/auth/logged", this.httpOptions).subscribe(user => {

      /* Fetching open session for current user */
      this.http.get<OpenSession>("/api/open/sessions/user/" + user.id, this.httpOptions).subscribe(openSession => {
        this.openSession = openSession;
        this.requestedOpenSession = true;

        /* Fetching parking space for current session */
        this.http.get<ParkingSpace>("/api/parking/space/" + openSession.parkingId, this.httpOptions).subscribe(parkingSpace => {
          this.parkingSpace = parkingSpace;
        }, error => {
          if (error.status === 403) {
            this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
            this.router.navigate(['/login']);
          } else {
            console.log(error);
            this.notification.show('warning', 'Failed fetching parking space for current open session', 'top', 'center');
          }
        });

      }, error => {
        if (error.status === 403) {
          this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
          this.router.navigate(['/login']);
        }
      });

    }, error => {
      this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
      this.router.navigate(['/login']);
    });
  }

  closeOpenSession() {
    this.http.get<Transaction>('/api/open/sessions/summary/' + this.openSession.id, this.httpOptions).subscribe(transaction => {
      this.currentSumary = transaction;
      console.log(transaction)
      const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
        width: '250px',
        data: {
          id: this.currentSumary.id,
          userId: this.currentSumary.userId,
          parkingId: this.currentSumary.parkingId,
          duration: this.currentSumary.duration,
          arrivalTime: this.currentSumary.arrivalTime,
          departureTime: this.currentSumary.departureTime,
          cost: this.currentSumary.cost
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    });


    /* Fetching open session for current user */
    this.http.delete<OpenSession>('/api/open/sessions/close/' + this.openSession.id, this.httpOptions).subscribe(openSession => {
      this.openSession = null;
      this.notification.show('success', 'Closed session for parking ' + this.parkingSpace.name, 'top', 'center');
    }, error => {
      if (error.status === 403) {
        this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
        this.router.navigate(['/login']);
      } else {
        this.notification.show('warning', 'Failed closing open parking session', 'top', 'center');
      }
    });



  }
}

@Component({
  selector: 'app-dialog-overview-example-dialog',
  templateUrl: 'dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {

  constructor(
      public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
      @Inject(MAT_DIALOG_DATA) public data: Transaction) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
