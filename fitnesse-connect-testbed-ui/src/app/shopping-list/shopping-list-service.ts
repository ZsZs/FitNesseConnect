import { Injectable } from '@angular/core';
import {Ingredient} from '../ingredient';

@Injectable()
export class ShoppingListService {
  private items: Ingredient[] = [];

  addItem( item: Ingredient ) {
    this.items.push( item );
  }

  addItems(items: Ingredient[]) {
    Array.prototype.push.apply(this.items, items);
  }

  deleteItem( item: Ingredient ) {
    this.items.splice( this.items.indexOf( item ), 1 );
  }

  editItem( oldItem: Ingredient, newItem: Ingredient ) {
    this.items[ this.items.indexOf( oldItem )] = newItem;
  }

  getItems() {
    return this.items;
  }

}
