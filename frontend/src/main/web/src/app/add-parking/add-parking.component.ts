import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { NotificationService } from '../service/notification.service';

import { ParkingSpace } from '../model/parking-space';
import { User } from '../model/user';

@Component({
  selector: 'app-add-parking',
  templateUrl: './add-parking.component.html',
  styles: ['']
})
export class AddParkingComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  public addParkingForm: FormGroup;

  costGranularities: any[] = [
    {value: 'COST_PER_10MIN', viewValue: '10 minutes'},
    {value: 'COST_PER_20MIN', viewValue: '20 minutes'},
    {value: 'COST_PER_30MIN', viewValue: '30 minutes'},
    {value: 'COST_PER_1H', viewValue: '1 hour'},
    {value: 'COST_PER_1D', viewValue: '1 day'},
  ];
  defaultCostGranularity = 'COST_PER_1H';

  constructor(private http: HttpClient, private router: Router, private notification: NotificationService) { }

  ngOnInit() {
    this.addParkingForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      address: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      capacity: new FormControl('', [Validators.required]),
      costGranularity: new FormControl('', [Validators.required]),
      cost: new FormControl('', [Validators.required])
    });
    this.addParkingForm.controls['costGranularity'].setValue(this.defaultCostGranularity, {onlySelf: true});
  }

  public hasError = (controlName: string, errorName: string) =>{
    return this.addParkingForm.controls[controlName].hasError(errorName);
  }

  public registerParking = (addParkingFormValue) => {
    if (this.addParkingForm.valid) {
      const newParkingSpace = {
        name: addParkingFormValue.name,
        address: addParkingFormValue.address,
        freeParkingSpaces: addParkingFormValue.capacity,
        totalParkingSpaces: addParkingFormValue.capacity,
        cost: addParkingFormValue.cost,
        costGranularity: addParkingFormValue.costGranularity
      };
      this.http.post<ParkingSpace>('/api/parking/space', newParkingSpace, this.httpOptions).subscribe(parkingSpace => {
        console.log(newParkingSpace);
        this.notification.show('success', 'Successfully created parking space', 'top','center');
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
  }
}
