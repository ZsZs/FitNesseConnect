import { Component, OnInit } from '@angular/core';
import {Car} from './car';
import {CarService} from './car.service';

@Component({
  selector: 'app-car',
  template: `
    <div class="row">
        <div class="col-md-5">
            <app-car-list></app-car-list>
        </div>
        <div class="col-md-7">
            <router-outlet></router-outlet>
        </div>
    </div>
    `,
  providers: [CarService]
})
export class CarComponent implements OnInit {
  selectedCar: Car;

  constructor() { }

  ngOnInit() {
  }

}
