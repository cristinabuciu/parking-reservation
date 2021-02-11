import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { User } from '../model/user';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  username: string = ''
  password: string = ''

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }),
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  public attemptResetPassword() {
    this.http.post<User>("/auth/password/reset", {
      username: this.username,
      password: this.password
    }, this.httpOptions).subscribe(data => {
      this.router.navigate(['/login']);
    }, error => {
    });
  }

}
