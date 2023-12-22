import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { User } from 'model/user';

@Injectable()
export class LoginService {

  private loginUrl:string;

  constructor(private http:HttpClient) { 
      this.loginUrl='http://localhost:8082/users-ws/users/login';
  }

  public login (user:User){
    return this.http.post<User>(this.loginUrl,user);
  }
}
