package com.processpuzzle.fitnesse.connect.rest;

public class UserSession implements Runnable {
   private final String name;
   
   // constructors
   public UserSession( final String name ){
      this.name = name;
   }
   
   // public accessors and mutators
   @Override
   public void run() {
   }

   // properties
   // @formatter:off
   public String getName(){  return this.name; }
   // @formatter:on
   
   // protected, private helper methods
}
