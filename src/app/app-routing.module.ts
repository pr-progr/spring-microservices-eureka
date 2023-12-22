import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentListComponent } from './student-list/student-list/student-list.component';
import { StudentFormComponent } from './student-form/student-form.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {path:'students',component:StudentListComponent},
  {path:'addstudent',component:StudentFormComponent},
  {path:'',component:LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
