import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';

@Component({
  selector: 'app-parking-spaces',
  templateUrl: './parking-spaces.component.html',
  styles: ['']
})
export class ParkingSpacesComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  searchParkingName: string = "";
  parkingSpaces: ParkingSpace[];
  filteredParkingSpaces: ParkingSpace[];

  constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

  ngOnInit() {
    /* Fetching logged user. Can be replaced with authentication service */
    this.http.get<User>("/auth/logged", this.httpOptions).subscribe(user => {

      /* Fetching parking spaces for current admin */
      this.http.get<ParkingSpace[]>("/api/parking/space/admin/" + user.id, this.httpOptions).subscribe(parkingSpaces => {
        this.parkingSpaces = parkingSpaces;
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

}
