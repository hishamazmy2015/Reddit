import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { LoginRequestPayload } from './loginRequest.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginReq: LoginRequestPayload;
  registerSuccessMessage: string;
  isError: boolean;

  constructor(private authSer: AuthService, private activatedRouter: ActivatedRoute,
    private tost: ToastrService, private router: Router) {
    this.loginReq = {
      username: '',
      password: '',
    }
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
    this.activatedRouter.queryParams.subscribe(params => {
      if (params.registered !== undefined && params.registered === 'true') {
        this.tost.success('Signup Successful');
        this.registerSuccessMessage = 'Please Check your inbox for activation then' +
          'activate your account before you login! ';

      }
    });


  }

  login() {
    this.loginReq.username = this.loginForm.get('username').value;
    this.loginReq.password = this.loginForm.get('password').value;
    this.authSer.login(this.loginReq).subscribe(
      date => {
        console.log('Login successful');
        if (date) {
          this.isError = false;
          this.router.navigateByUrl('/');
          this.tost.success('Login Successful');

        } else {
          this.isError = true;
        }
      }
    );
  }



}
