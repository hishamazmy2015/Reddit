import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn=false;
  username:string;

  constructor() { }

  ngOnInit(): void {
  }


  goToUserProfile(){

  }
  logout(){

  }





}
