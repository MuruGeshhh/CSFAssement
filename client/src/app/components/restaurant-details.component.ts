import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit {
	
	// TODO Task 4 and Task 5
	// For View 
  
  comment!: FormGroup


  constructor(private fb: FormBuilder, private router: Router, private ressvc: RestaurantService){}



ngOnInit(): void {
  this.comment = this.createForm()
  this.comment.reset()
}

private createForm() {
  return this.fb.group({
    name: this.fb.control<string>(''),
    rating: this.fb.control<string>(''),
    comments: this.fb.control<string>(''),  
  })
}

}