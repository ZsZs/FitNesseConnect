import {Component, OnInit, Input, OnChanges, Output, EventEmitter} from '@angular/core';
import {Ingredient} from '../ingredient';
import {ShoppingListService} from './shopping-list-service';

@Component({
  selector: 'app-shopping-list-add',
  templateUrl: './shopping-list-add.component.html',
  styles: []
})
export class ShoppingListAddComponent implements OnChanges {
  isAdd = true;
  @Output() cleared = new EventEmitter();
  @Input() item: Ingredient;

  constructor( private shoppingListService: ShoppingListService ) { }

  ngOnChanges( changes ) {
    if ( changes.item.currentValue === null ) {
      this.isAdd = true;
      this.item = { name: null, amount: null };
    }else {
      this.isAdd = false;
    }
  }

  onClear() {
    this.isAdd = true;
    this.cleared.emit( null );
  }

  onDelete() {
    this.shoppingListService.deleteItem( this.item );
    this.onClear();
  }

  onSubmit( ingredient: Ingredient ) {
    if ( !this.isAdd ) {
      this.shoppingListService.editItem( this.item, ingredient );
    }else {
      this.item = ingredient;
      this.shoppingListService.addItem( ingredient );
    }
  }
}
