import {Component, OnInit, EventEmitter, Output} from '@angular/core';
import {Car} from "./car";
import {CarService} from "./car.service";

@Component({
  selector: 'app-car-list',
  template: `
    <div class="row">
      <div class="col-xs-12">
        <a class="btn btn-success" [routerLink]="'new'">New Car</a>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-12">
        <ul class="list-group">
          <app-car-list-item *ngFor="let car of cars; let i = index" [car]="car" [carId]="car.id"></app-car-list-item>
        </ul>
      </div>
    </div>
`
})

export class CarListComponent implements OnInit {
  cars: Car[] = [];
  carsChanged = new EventEmitter<Car[]>();

  constructor( private carService: CarService ) { }

  ngOnInit() {
    this.carService.getCars().subscribe(
       ( data: Car[]) => {
         this.cars = data;
       }
    );
  }

  onSelected( car: Car ){
  }
}
