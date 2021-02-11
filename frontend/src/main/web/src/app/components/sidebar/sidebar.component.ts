import { Component, OnInit } from '@angular/core';

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    roles: string[];
}
export const ROUTES: RouteInfo[] = [
    { path: '/parking-spaces', title: 'My parkings', icon: 'person', class: '', roles: ['ADMIN'] },
    { path: '/add-parking', title: 'Add new parking', icon: 'dashboard', class: '', roles: ['ADMIN'] },
    { path: '/reports', title: 'Reports', icon: 'content_paste', class: '', roles: ['ADMIN'] },
    { path: '/find-parking', title: 'Find new parking', icon: 'library_books', class: '', roles: ['USER'] },
    { path: '/current-session', title: 'Check current session', icon: 'bubble_chart', class: '', roles: ['USER'] },
    { path: '/session-history', title: 'Check sessions history', icon: 'dashboard', class: '', roles: ['USER'] },
    { path: '/add-car', title: 'Register new car plate', icon: 'library_books', class: '', roles: ['USER']},
    { path: '/login', title: 'Logout', icon: 'person', class: '', roles: ['ADMIN', 'USER'] }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor() { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };
  hasRole(roles: string[]) {
    return roles.includes(sessionStorage.getItem('userRole'));
  }
}
