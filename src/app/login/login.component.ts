import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'model/user';
import { LoginService } from 'service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  successLogin =false;
  'user':User;

  constructor(private route: ActivatedRoute, private loginService:LoginService, private router:Router){
    this.user=new User();
  }
  
  handleLogin() {
       this.loginService.login(this.user).subscribe(_res=> this.goToStudentList() );
       this.successLogin=true;
      }

    goToStudentList(){
      this.router.navigate(['/students']);
    }
}
