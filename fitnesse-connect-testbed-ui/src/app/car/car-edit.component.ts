import {Component, EventEmitter, OnInit, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {CarService} from './car.service';
import {Car} from './car';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {error} from 'util';

@Component({
  selector: 'app-car-edit',
  templateUrl: './car-edit.component.html'
})
export class CarEditComponent implements OnDestroy, OnInit {
  private car: Car;
  public carEditForm: FormGroup;
  private carIndex: number;
  private isNew = true;
  private subscription: Subscription;

  // constructors
  constructor( private route: ActivatedRoute, private router: Router, private formBuilder: FormBuilder, private carService: CarService ) { }

  // public accessors and mutators
  navigateBack() {
    this.router.navigate( ['../'] );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(
       ( params: any ) => {
         if ( params.hasOwnProperty( 'id' )) {
           this.isNew = false;
           this.carIndex = +params['id'];
           this.carService.getCar( this.carIndex ).subscribe(
              ( data: Car ) => {
                this.car = data;
                this.updateForm();
              }
           );
         }else {
           this.isNew = true;
           this.car = null;
         }
         this.initForm();
       }
    );
  }

  onCancel() {
    this.navigateBack();
  }

  onSubmit() {
    const newCar = this.carEditForm.value;
    newCar.id = this.carIndex;

    if ( this.isNew ) {
      this.carService.add( newCar ).subscribe(
        data => console.log( data ),
        error => console.log( error )
      );
    }else {
      this.carService.update( newCar ).subscribe();
    }

    this.navigateBack();
  }

  // protected, private helper methods
  private initForm() {
    let carMake, carModel, carDescription, carImageUrl = '';
    if ( !this.isNew && this.car ) {
      carMake = this.car.make; carModel = this.car.model; carDescription = this.car.description; carImageUrl = this.car.imageUrl;
    }

    this.carEditForm = this.formBuilder.group({
      make: [carMake, Validators.required],
      model: [carModel, Validators.required],
      description: [carDescription, Validators.required],
      imageUrl: [carImageUrl, Validators.required]
    });
  }

  private updateForm() {
    (<FormControl>this.carEditForm.controls['make']).setValue( this.car.make, { onlySelf: true });
    (<FormControl>this.carEditForm.controls['model']).setValue( this.car.model, { onlySelf: true });
    (<FormControl>this.carEditForm.controls['description']).setValue( this.car.description, { onlySelf: true });
    (<FormControl>this.carEditForm.controls['imageUrl']).setValue( this.car.imageUrl, { onlySelf: true });
  }
}
