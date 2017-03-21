package com.processpuzzle.fitnesse.connect.json;

public class VerifyJsonProperties {
   private static final String PASS_TOKEN = "pass";
   private final String jsonObject;
   private String propertyDescription;
   private boolean propertyMandatory;
   private String propertyName;
   private PropertyDataTypes propertyType;
   private boolean propertyValueNotNull;
   private String returnMessage = "pass";
    
   // constructors
   public VerifyJsonProperties( String jsonObject ){
      this.jsonObject = jsonObject;
   }
    
   // public accessors and mutators
   public String verifyProperty(){
      determineCurrentProperty();
      verifyPropertyName();
      return this.returnMessage;
   } 
   
   // properties
   // @formatter:off
   public void setProperty( String propertyName ){ this.propertyName = propertyName; }
   public void setDataType( String propertyType ){ this.propertyType = PropertyDataTypes.parse( propertyType ); }
   public void setDescription( String propertyDescription ){ this.propertyDescription = propertyDescription; }
   public void setIsMandatory( String propertyMandatory ){ this.propertyMandatory = "yes".equals( propertyMandatory ); }
   public void setNotNull( String propertyValueNotNull ){ this.propertyValueNotNull = "yes".equals( propertyValueNotNull ); }
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
   }

   private void verifyPropertyName() {
      String result = PASS_TOKEN;
      if( this.propertyMandatory ){
         if( this.columnIndex != null ){
            result = PASS_TOKEN;
         }else{
            result = "failed: property not defined";
         }
      }
      
      combineReturnMessage( result );
   }

}