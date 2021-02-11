import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { User } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

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
    var userRole = sessionStorage.getItem('userRole');
    if (typeof userRole !== 'undefined' && userRole != null) {
      this.http.delete("/auth/logout", this.httpOptions).subscribe(data => {
      }, error => {
      });
      sessionStorage.removeItem('userRole');
    }
  }

  public attemptLogin() {
    this.http.post<User>("/auth/login", {
      username: this.username,
      password: this.password
    }, this.httpOptions).subscribe(user => {
      sessionStorage.setItem('userRole', user.role);
      this.router.navigate(['/parking-spaces']);
    }, error => {
    });
  }

  public goRegister() {
    this.router.navigate(['/register']);
  }

}
