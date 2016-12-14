package com.processpuzzle.fitnesse.connect.resource;

public enum ResourceProperties {
   FILE_NAME( "file name"), SIZE( "size" ), UNKNOWN( "unknown" );
   
   ResourceProperties( String cannonicalName ){
      this.cannonicalName = cannonicalName;
   }
   
   private final String cannonicalName;
   
   // properties
   // @formatter:off
   public String getCannonicalName(){ return this.cannonicalName; }
   // @formatter:on
}
