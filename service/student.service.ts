import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Student } from 'model/student';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class StudentService {

    private studentUrl:string;

    constructor(private http:HttpClient) {
      this.studentUrl='http://localhost:8080/students';
    }

    public findAll():Observable<Student[]>{
      return this.http.get<Student[]>(this.studentUrl);
    }

    public save(student:Student){
      return this.http.post<Student>(this.studentUrl,student);
    }

}
