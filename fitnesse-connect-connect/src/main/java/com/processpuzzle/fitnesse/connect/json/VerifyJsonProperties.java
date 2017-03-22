package com.processpuzzle.fitnesse.connect.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class VerifyJsonProperties {
   private static final Logger log = LoggerFactory.getLogger(  VerifyJsonProperties.class );
   private static final String PASS_TOKEN = "pass";
   private final String jsonObject;
   private String propertyDescription;
   private boolean propertyMandatory;
   private String propertySelector;
   private PropertyDataTypes propertyType;
   private String propertyValue;
   private boolean propertyValueNotNull;
   private String returnMessage = "pass";

   // constructors
   public VerifyJsonProperties( String jsonObject ) {
      this.jsonObject = jsonObject;
   }

   // public accessors and mutators
   public String verifyProperty() {
      determineCurrentProperty();
      verifyPropertyName();
      return this.returnMessage;
   }

   // properties
   // @formatter:off
   public void setProperty( String propertyName ) { this.propertySelector = propertyName; }
   public void setDataType( String propertyType ) { this.propertyType = PropertyDataTypes.parse( propertyType ); }
   public void setDescription( String propertyDescription ) { this.propertyDescription = propertyDescription; }
   public void setIsMandatory( String propertyMandatory ) { this.propertyMandatory = "yes".equals( propertyMandatory ); }
   public void setNotNull( String propertyValueNotNull ) { this.propertyValueNotNull = "yes".equals( propertyValueNotNull ); }
   // @formatter:on

   // protected, private helper methods
   private void combineReturnMessage( String additionalMessage ) {
      if( additionalMessage.equals( PASS_TOKEN ) ){
         if( this.returnMessage == null || this.returnMessage.isEmpty() ){
            this.returnMessage = PASS_TOKEN;
         }
      }else if( this.returnMessage != null && this.returnMessage.equals( PASS_TOKEN ) ){
         this.returnMessage = additionalMessage;
      }else if( this.returnMessage != null ){
         this.returnMessage += "; " + additionalMessage;
      }else{
         this.returnMessage = additionalMessage;
      }
   }

   private void determineCurrentProperty() {
      try{
         this.propertyValue = JsonPath.parse( jsonObject ).read( this.propertySelector ).toString();         
      }catch( PathNotFoundException e ){
         log.info( "Selecting property: " + this.propertySelector + " failed." );
      }
   }

   private void verifyPropertyName() {
      String result = PASS_TOKEN;
      if( this.propertyMandatory ){
         if( this.propertyValue != null ){
            result = PASS_TOKEN;
         }else{
            result = "failed: property not defined";
         }
      }

      combineReturnMessage( result );
   }

}