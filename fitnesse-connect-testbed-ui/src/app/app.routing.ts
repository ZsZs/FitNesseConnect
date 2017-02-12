import {Routes} from "@angular/router";
import {AboutComponent} from "./about/about.component";
import {CarComponent} from "./car/car.component";
import {FileComponent} from "./file/file.component";
import {ShoppingListComponent} from "./shopping-list/shopping-list.component";
import {UserComponent} from "./user/user.component";
import {CAR_ROUTES} from "./car/car.routes";

export const APP_ROUTES: Routes = [
   { path: '', redirectTo: 'cars', pathMatch: 'full' },
   { path: 'cars', component: CarComponent, children: CAR_ROUTES },
   { path: 'files', component: FileComponent },
   { path: 'busket', component: ShoppingListComponent },
   { path: 'user', component: UserComponent },
   { path: 'about', component: AboutComponent }
];

