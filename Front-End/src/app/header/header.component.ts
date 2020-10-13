import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/shared/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;
  username: string;

  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.auth.isLoggedIn();
    this.username = this.auth.getUserName();
  }


  goToUserProfile() {
    this.router.navigateByUrl("/user-profile/" + this.username);

  }
  logout() {

  }





}
