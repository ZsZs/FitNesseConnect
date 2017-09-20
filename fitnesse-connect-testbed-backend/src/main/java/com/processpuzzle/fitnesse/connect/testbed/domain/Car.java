package com.processpuzzle.fitnesse.connect.testbed.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car{
   @Id @GeneratedValue private Long id;
   private String color;
   private String description;
   private String make;
   private String model;
   private String imageUrl;
   private Integer year;

   // constructurs
   Car() {}

   public Car( String make, String model, Integer year, String color ) {
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
   
   public void update( Car car ) {
      if( car.getColor() != null ) color = car.getColor();
      if( car.getDescription() != null ) description = car.getDescription();
      if( car.getMake() != null ) make = car.getMake();
      if( car.getModel() != null ) model = car.getModel();
      if( car.getImageUrl() != null ) imageUrl = car.getImageUrl();
      if( car.getYear() != null ) year = car.getYear();
   }
   
   // properties
   // @formatter:off
   public String getColor() { return color; }
   public String getDescription() { return description; }
   public Long getId() { return id; }
   public String getImageUrl() { return imageUrl; }
   public String getMake() { return make; }
   public String getModel() { return model; }
   public Integer getYear() { return year; }
   public void setColor( String color ) { this.color = color; }
   public void setDescription( String description ) { this.description = description; }
   public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }
   public void setMake( String make ) { this.make = make; }
   public void setModel( String model ) { this.model = model; }
   public void setYear( Integer year ) { this.year = year; }
   // @formatter:on
}
