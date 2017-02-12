import { EventEmitter, Injectable } from '@angular/core';
import {Headers, Http, Response} from '@angular/http';
import 'rxjs/Rx';
import { Observable } from 'rxjs';
import {Car} from "./car";

@Injectable()
export class CarService {
  private hostUrl = 'http://localhost:9124';
  private serviceUrl = this.hostUrl + '/cars';

  constructor( private http: Http ){}

  add( newCar: Car ): Observable<Response>{
    const body = JSON.stringify( newCar );
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    return this.http.post( this.serviceUrl, body, { headers: headers });
  }

  deteleCar( carToRefactor: Car ): Observable<any>{
    let resourceUrl = this.serviceUrl + '/' + carToRefactor.id;
    return this.http.delete( resourceUrl );
  }

  getCar( index: number ): Observable<Car> {
    let resourceUrl = this.serviceUrl + '/' + index;
    return this.http.get( resourceUrl ).map(
       ( response: Response ) => response.json()
    );
  }

  getCars(): Observable<Car[]> {
    const body = '';
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    return this.http.get( this.serviceUrl, { headers: headers } ).map(
       (response: Response) => response.json()
    );
  }

  update( car: Car ): Observable<Car>{
    const body = JSON.stringify( car );
    const headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' });
    let resourceUrl = this.serviceUrl + '/' + car.id;
    return this.http.put( resourceUrl, body, { headers: headers }).map(
       (response: Response) => response.json()
    );
  }
}
