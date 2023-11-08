import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Student } from 'model/student';
import { StudentService } from 'service/student.service';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  student:Student;

  constructor(private route: ActivatedRoute, private studentService:StudentService, private router:Router){
    this.student=new Student();
  }

  onSubmit(){
    this.studentService.save(this.student).subscribe(_result => this.goToStudentList());
  }

  goToStudentList(){
    this.router.navigate(['/students']);
  }
}



