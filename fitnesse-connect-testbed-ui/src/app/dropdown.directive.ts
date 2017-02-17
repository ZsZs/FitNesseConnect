import {Directive, HostBinding, HostListener} from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
export class DropdownDirective {
  private isOpen = false;

  @HostBinding( 'class.open' ) get opened(){
    return this.isOpen;
  }

  @HostListener('mouseleave') close() {
    this.isOpen = false;
  }

  @HostListener('click') open() {
    this.isOpen = true;
  }
}
