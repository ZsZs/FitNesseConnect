import { EventEmitter, Injectable } from '@angular/core';
import {Headers, Http, Response} from '@angular/http';
import 'rxjs/Rx';
import { Observable } from 'rxjs';
import {Car} from './car';
import { environment } from '../../environments/environment';

@Injectable()
export class CarService {
  private resourcePath = 'cars';

  // constructors
  constructor( private http: Http ) {}

  // public accessors and mutators
  add( newCar: Car ): Observable<Response> {
    const body = JSON.stringify( newCar );
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    return this.http.post( this.buildResourceUrl(), body, { headers: headers });
  }

  deteleCar( carToRefactor: Car ): Observable<any> {
    let resourceUrl = this.buildResourceUrl( '/' + carToRefactor.id );
    return this.http.delete( resourceUrl );
  }

  getCar( index: number ): Observable<Car> {
    return this.http.get( this.buildResourceUrl( '/' + index )).map(
       ( response: Response ) => response.json()
    );
  }

  getCars(): Observable<Car[]> {
    const body = '';
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    return this.http.get( this.buildResourceUrl(), { headers: headers } ).map(
       (response: Response) => response.json()
    );
  }

  update( car: Car ): Observable<Car> {
    const body = JSON.stringify( car );
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    return this.http.put( this.buildResourceUrl( '/' + car.id ), body, { headers: headers }).map(
       (response: Response) => response.json()
    );
  }

  // protected, private helper methods
  private buildResourceUrl( subResource?: string ): string {
    let resourceUrl = environment.carService.protocol;
    resourceUrl += '//' + environment.carService.host;
    resourceUrl += environment.carService.contextPath != null ? '/' + environment.carService.contextPath : '';
    resourceUrl += '/' + this.resourcePath;
    resourceUrl += subResource != null ? '/' + subResource : '';
    return resourceUrl;
  }
}
