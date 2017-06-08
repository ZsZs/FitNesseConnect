package com.processpuzzle.fitnesse.connect.testbed.domain;

public class SecuredObject {
   private final String name;
   
   // constructors
   public SecuredObject( String name ){
      this.name = name;
   }
   
   // properties
   // @formatter:off
   public String getName(){ return this.name; }
   // @formatter:on
}
