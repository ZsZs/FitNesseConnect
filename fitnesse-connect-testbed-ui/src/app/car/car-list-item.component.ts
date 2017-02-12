import {Component, Input} from '@angular/core';
import {Car} from "./car";

@Component({
  selector: 'app-car-list-item',
  template: `
    <a [routerLink]="[carId]" class="list-group-item clearfix" routerLinkActive = "active">
      <div class="pull-left">
        <h4 class="list-group-item-heading">{{car.make}}, {{car.model}}</h4>
        <p class="list-group-item-text">{{car.description}}</p>
      </div>
      <span class="pull-right">
          <img class="img-responsive" src="{{car.imageUrl}}" style="max-height: 50px;"/>
      </span>
    </a>
`
})

export class CarItemComponent {
  @Input() car: Car;
  @Input() carId: number;
}
