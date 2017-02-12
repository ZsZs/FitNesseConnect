export class Car {
   id: number;
   colour: string;
   description: string;
   make: string;
   model: string;
   imageUrl: string;
   year: string;

   constructor( make: string, model: string, description: string, imageUrl: string, year: string ){
      this.make = make;
      this.model = model;
      this.description = description;
      this.imageUrl = imageUrl;
      this.year = year;
   }
}
