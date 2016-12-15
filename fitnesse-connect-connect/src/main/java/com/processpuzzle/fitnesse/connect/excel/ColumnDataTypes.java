package com.processpuzzle.fitnesse.connect.excel;

public enum ColumnDataTypes {
   STRING( "String" ), INTEGER( "Integer" ), DATE( "Date" );
   
   private ColumnDataTypes( final String cannonicalName ){
      this.cannonicalName = cannonicalName;
   }
   
   private final String cannonicalName;
   
   // public accessors and motators
   public static ColumnDataTypes parse( String dataTypeName ){
      ColumnDataTypes actualDataType = null;
      
      for( ColumnDataTypes dataType : ColumnDataTypes.values() ){
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
