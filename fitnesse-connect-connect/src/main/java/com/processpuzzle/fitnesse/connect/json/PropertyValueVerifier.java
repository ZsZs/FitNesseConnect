package com.processpuzzle.fitnesse.connect.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PropertyValueVerifier {
   private final String value;

   public PropertyValueVerifier( String value ) {
      this.value = value;
   }

   public boolean isTypeOf( PropertyDataTypes propertyType ) {
      boolean isTypeOf = false;

      switch( propertyType ) {
         case STRING:
            isTypeOf = true;
            break;
         case FLOAT:
            isTypeOf = isTypeOfFloat();
            break;
         case INTEGER:
            isTypeOf = isTypeOfInteger();
            break;
         case BOOLEAN:
            isTypeOf = isTypeOfBoolean();
            break;
         case DATE:
            isTypeOf = isTypeOfDate();
            break;
         default:
            isTypeOf = false;
      }
      return isTypeOf;
   }

   public boolean isTypeOfDate(){
      return isValidDateFormat( "yyyy.MM.dd" );
   }
   
   public boolean isTypeOfBoolean() {
      boolean isBoolean = false;
      try{
         if( !Boolean.parseBoolean( this.value )){
            if( this.value.equalsIgnoreCase( "yes" ) || this.value.equalsIgnoreCase( "no" ) || this.value.equalsIgnoreCase( "false" )) {
               isBoolean = true;                        
            }else{
               isBoolean = false;
            }
         }else{
            isBoolean = true;
         }
      }catch( Exception e ){
         isBoolean = false;
      }
      
      return isBoolean;
   }
   
   public boolean isTypeOfFloat() {
      boolean isFloat = false;
      try{
         Double.parseDouble( this.value );
         isFloat = true;
      }catch( Exception e ){
         isFloat = false;
      }
      
      return isFloat;
   }

   public boolean isTypeOfInteger() {
      boolean isInteger = false;
      try{
         Integer.parseInt( this.value );
         isInteger = true;
      }catch( Exception e ){
         isInteger = false;
      }
      
      return isInteger;
   }
   
   public boolean isTypeOfString(){
      return true;
   }

   // protected, private helper methods
   private boolean isValidDateFormat( String format ) {
      Date date = null;
      try{
         SimpleDateFormat sdf = new SimpleDateFormat( format );
         date = sdf.parse( value );
         if( !value.equals( sdf.format( date ) ) ){
            date = null;
         }
      }catch( ParseException ex ){
         // sip the exception
      }
      return date != null;
   }

}
