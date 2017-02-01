import { Injectable } from '@angular/core';
import {Car} from "./car";

@Injectable()
export class CarService {
  private cars: Car[] = [
    new Car( "Ford", "Mustang", "Mustang Shellby", "http://www.ford.com/ngbs-services/resources/ford/mustang/2017/highlights/mst17_highlight_lg_extdesign.jpg", "2016"),
    new Car( "Land Rover", "4x4 Discovery Sport", "4x4 Land Rover Discovery", "http://images05.noen.at/LR5.jpg/teaser-col-8/4.587.377", "2016"),
    new Car( "BMW", "Z4", "BMW Z4 roadster", "http://buyersguide.caranddriver.com/media/assets/submodel/6937.jpg", "2016")
  ];

  add( newCar: Car ){
    this.cars.push( newCar );
  }

  deteleCar( carToRefactor: Car ){
    this.cars.splice( this.cars.indexOf( carToRefactor ), 1 );
  }

  getCar( index: number ): Car {
    return this.cars[index];
  }

  getCars(): Car[]{
    return this.cars;
  }

  update( oldCar: Car, newCar: Car ){
    this.cars[this.cars.indexOf(oldCar)] = newCar;
  }
}
