import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {CarComponent} from './car/car.component';
import {FileComponent} from './file/file.component';
import {HeaderComponent} from './header.component';
import {CarListComponent} from './car/car-list.component';
import {CarItemComponent} from './car/car-list-item.component';
import {CarDetailComponent} from './car/car-detail.component';
import {DropdownDirective} from './dropdown.directive';
import {APP_ROUTES} from './app.routing';
import {RouterModule} from '@angular/router';
import { CarEditComponent } from './car/car-edit.component';
import { CarHomeComponent } from './car/car-home.component';
import { ShoppingListComponent } from './shopping-list/shopping-list.component';
import { ShoppingListAddComponent } from './shopping-list/shopping-list-add.component';
import { UserComponent } from './user/user.component';
import { AboutComponent } from './about/about.component';

@NgModule({
   declarations: [
      AppComponent,
      CarComponent,
      FileComponent,
      HeaderComponent,
      CarListComponent,
      CarItemComponent,
      CarDetailComponent,
      DropdownDirective,
      CarEditComponent,
      CarEditComponent,
      CarHomeComponent,
      ShoppingListComponent,
      ShoppingListAddComponent,
      UserComponent,
      AboutComponent
   ],
   imports: [
      BrowserModule,
      FormsModule,
      ReactiveFormsModule,
      HttpModule,
      RouterModule.forRoot( APP_ROUTES )
   ],
   providers: [],
   bootstrap: [AppComponent]
})
export class AppModule {
}
