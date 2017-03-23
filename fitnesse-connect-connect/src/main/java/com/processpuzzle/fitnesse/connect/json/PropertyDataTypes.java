package com.processpuzzle.fitnesse.connect.json;

public enum PropertyDataTypes {
   STRING( "String" ), FLOAT( "Float" ), INTEGER( "Integer" ), LONG( "Long" ), DATE( "Date" ), BOOLEAN( "Boolean" );
   
   private PropertyDataTypes( final String cannonicalName ){
      this.cannonicalName = cannonicalName;
   }
   
   private final String cannonicalName;
   
   // public accessors and motators
   public static PropertyDataTypes parse( String dataTypeName ){
      PropertyDataTypes actualDataType = null;
      
      for( PropertyDataTypes dataType : PropertyDataTypes.values() ){
         if( dataTypeName.equals( dataType.getCannonicalName() )){
            actualDataType = dataType;
            break;
         }
      }
      
      return actualDataType;
   }
   
   // properties
   // @formatter:off
   public String getCannonicalName(){ return this.cannonicalName; }
   // @formatter:on
}
