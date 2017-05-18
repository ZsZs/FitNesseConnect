package com.processpuzzle.fitnesse.connect.rest;

public class TestObject {
   private String textValue;
   private Integer numberValue;

   // constructors
   TestObject(){
      this( null, null );
   }
   
   TestObject( String textValue, Integer numberValue ) {
      this.textValue = textValue;
      this.numberValue = numberValue;
   }

   // properties
   // @formatter:off
   public Integer getNumberValue() { return numberValue; }
   public String getTextValue() { return textValue; }
   public void setNumberValue( Integer numberValue ) { this.numberValue = numberValue; }
   public void setTextValue( String textValue ) { this.textValue = textValue; }
   // @formatter:on
}
