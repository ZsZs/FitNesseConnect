import { Component, OnInit } from '@angular/core';
import {Ingredient} from "../ingredient";
import {ShoppingListService} from "./shopping-list-service";

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  providers: [ShoppingListService]
})

export class ShoppingListComponent implements OnInit {
  items: Ingredient[] = [];
  selectedItem: Ingredient = null;

  constructor(private shoppingListService: ShoppingListService) {}

  ngOnInit() {
    this.items = this.shoppingListService.getItems();
  }

  onCleared(){
    this.selectedItem = null;
  }

  onSelectItem( item: Ingredient ){
    this.selectedItem = item;
  }
}
