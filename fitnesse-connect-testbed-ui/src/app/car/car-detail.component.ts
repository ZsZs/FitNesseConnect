import {Component, OnInit, Input, OnDestroy} from '@angular/core';
import {Car} from './car';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {CarService} from './car.service';

@Component({
  selector: 'app-car-detail',
  templateUrl: './car-detail.component.html'
})

export class CarDetailComponent implements OnDestroy, OnInit {
  carIndex: number;
  selectedCar: Car;
  subscription: Subscription;

  constructor( private route: ActivatedRoute, private router: Router, private carService: CarService ) { }

  ngOnDestroy() {
     this.subscription.unsubscribe();
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(
       ( params: any ) => {
         this.carIndex = params['id'];
         this.carService.getCar( this.carIndex ).subscribe(
            ( data: Car ) => {
               this.selectedCar = data;
            }
         );
       }
    );
  }

  onDeleteCar() {
     this.carService.deteleCar( this.selectedCar ).subscribe();
     this.router.navigate(['/cars']);
  }

  onEditCar() {
     this.router.navigate(['/cars', this.selectedCar.id, 'edit' ]);
  }
}
