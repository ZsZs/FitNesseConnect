import {Component, OnInit, OnDestroy} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {CarService} from "./car.service";
import {Car} from "./car";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-car-edit',
  templateUrl: './car-edit.component.html'
})
export class CarEditComponent implements OnDestroy, OnInit {
  private car: Car;
  private carEditForm: FormGroup;
  private carIndex: number;
  private isNew = true;
  private subscription: Subscription;

  // constructors
  constructor( private route: ActivatedRoute, private router: Router, private formBuilder: FormBuilder, private carService: CarService ) { }

  // public accessors and mutators
  navigateBack(){
    this.router.navigate( ['../'] );
  }

  ngOnDestroy(){
    this.subscription.unsubscribe();
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(
       ( params: any ) => {
         if( params.hasOwnProperty( 'id' )){
           this.isNew = false;
           this.carIndex = +params['id'];
           this.car = this.carService.getCar( this.carIndex );
         }else {
           this.isNew = true;
           this.car = null;
         }

         this.initForm();
       }
    );
  }

  onCancel(){
    this.navigateBack();
  }

  onSubmit(){
    const newCar = this.carEditForm.value;
    if( this.isNew ){
      this.carService.add( newCar );
    }else{
      this.carService.update( this.car, newCar );
    }

    this.navigateBack();
  }

  // protected, private helper methods
  private initForm(){
    let carMake, carModel, carDescription, carImageUrl = '';
    if( !this.isNew ){
      carMake = this.car.make; carModel = this.car.model; carDescription = this.car.description; carImageUrl = this.car.imageUrl;
    }

    this.carEditForm = this.formBuilder.group({
      make: [carMake, Validators.required],
      model: [carModel, Validators.required],
      description: [carDescription, Validators.required],
      imageUrl: [carImageUrl, Validators.required]
    });
  }
}
