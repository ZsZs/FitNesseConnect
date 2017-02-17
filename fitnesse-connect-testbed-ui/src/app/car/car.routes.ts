import {Routes} from '@angular/router';
import {CarDetailComponent} from './car-detail.component';
import {CarEditComponent} from './car-edit.component';
import {CarHomeComponent} from './car-home.component';

export const CAR_ROUTES: Routes = [
   { path: '', component: CarHomeComponent },
   { path: 'new', component: CarEditComponent },
   { path: ':id', component: CarDetailComponent },
   { path: ':id/edit', component: CarEditComponent }
];
