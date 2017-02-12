package com.processpuzzle.fitnesse.connect.testbed.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car{
   @Id @GeneratedValue private long id;
   private String color;
   private String description;
   private String make;
   private String model;
   private String imageUrl;
   private int year;

   // constructurs
   Car() {}

   public Car( String make, String model, int year, String color ) {
      super();
      this.make = make;
      this.model = model;
      this.year = year;
      this.color = color;
   }

   // public accessors and mutators
   @Override public String toString() {
      return make + " " + model + " " + year + " " + this.color;
   }
   
   // properties
   // @formatter:off
   public String getColor() { return color; }
   public String getDescription() { return description; }
   public long getId() { return id; }
   public String getImageUrl() { return imageUrl; }
   public String getMake() { return make; }
   public String getModel() { return model; }
   public int getYear() { return year; }
   public void setColor( String color ) { this.color = color; }
   public void setDescription( String description ) { this.description = description; }
   public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }
   public void setMake( String make ) { this.make = make; }
   public void setModel( String model ) { this.model = model; }
   public void setYear( int year ) { this.year = year; }
   // @formatter:on

}
