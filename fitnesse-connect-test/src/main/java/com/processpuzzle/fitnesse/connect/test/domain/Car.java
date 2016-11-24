package com.processpuzzle.fitnesse.connect.test.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car{
   @Id @GeneratedValue private long id;
   private String color;
   private String make;
   private String model;
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
   public String getMake() { return make; }
   public String getModel() { return model; }
   public int getYear() { return year; }
   public String getColor() { return color; }
   // @formatter:on
}
