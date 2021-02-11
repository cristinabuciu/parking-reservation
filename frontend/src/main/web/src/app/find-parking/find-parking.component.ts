import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { NotificationService } from '../service/notification.service';

import { OpenSession } from '../model/open-session';
import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';
import { RegisteredCar } from '../model/registered-car';

@Component({
  selector: 'app-find-parking',
  templateUrl: './find-parking.component.html',
  styles: ['']
})
export class FindParkingComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  searchParkingName: string = "";
  parkingSpaces: ParkingSpace[];
  filteredParkingSpaces: ParkingSpace[];

  openSession: OpenSession;
  requestedOpenSession: boolean = false;

  registeredCars: RegisteredCar[];
  requestedRegisteredCars: boolean = false;
  selectedRegisteredCar: RegisteredCar;

  constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

  ngOnInit() {
    /* Fetching logged user. Can be replaced with authentication service */
    this.http.get<User>("/auth/logged", this.httpOptions).subscribe(user => {

      /* Fetching parking spaces for current user */
      this.http.get<ParkingSpace[]>("/api/parking/space", this.httpOptions).subscribe(parkingSpaces => {
        this.parkingSpaces = parkingSpaces;
        console.log(this.parkingSpaces)
        this.assignCopy();
      }, error => {
        if (error.status === 403) {
          this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
          this.router.navigate(['/login']);
        } else {
          console.log(error);
          this.notification.show('warning', 'Failed fetching parking spaces', 'top', 'center');
        }
      });

      /* Fetching open session for current user */
      this.http.get<OpenSession>("/api/open/sessions/user/" + user.id, this.httpOptions).subscribe(openSession => {
        this.openSession = openSession;
        this.requestedOpenSession = true;
      }, error => {
        if (error.status == 403) {
          this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
          this.router.navigate(['/login']);
        }
      });

      /* Fetching registered cars for current user */
      this.http.get<RegisteredCar[]>("/api/registered/cars/user/" + user.id, this.httpOptions).subscribe(registeredCars => {
        this.registeredCars = registeredCars;
        this.requestedRegisteredCars = true;
        if (this.registeredCars.length > 0) {
          this.selectedRegisteredCar = this.registeredCars[0];
        }
      }, error => {
        if (error.status == 403) {
          this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
          this.router.navigate(['/login']);
        }
      });

    }, error => {
      this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
      this.router.navigate(['/login']);
    });
  }

  private assignCopy(){
     this.filteredParkingSpaces = Object.assign([], this.parkingSpaces);
  }
  filterParkingSpaces(value){
     if(!value){
         this.assignCopy();
     } // when nothing has typed
     this.filteredParkingSpaces = Object.assign([], this.parkingSpaces).filter(
        item => item.name.toLowerCase().includes(value.toLowerCase())
     )
  }

  createOpenSession(parking, carId) {
      var newOpenSession = {
        parkingId: parking.id,
        carId: carId
      };
      this.http.post<OpenSession>("/api/open/sessions", newOpenSession, this.httpOptions).subscribe(openSession => {
        this.openSession = openSession;
        this.notification.show('success', 'Opened session for parking ' + parking.name, 'top', 'center');
      }, error => {
        if (error.status == 403) {
          this.notification.show('warning', 'User no longer authenticated. Redirecting to login', 'top', 'center');
          this.router.navigate(['/login']);
        }
      });
  }

}
