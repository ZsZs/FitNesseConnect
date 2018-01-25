package com.processpuzzle.fitnesse.connect.json;

import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class VerifyJsonProperties {
   public static final String FAILED_PROPERTY_VALUE_NOT_NULL = "failed: property value can't be null";
   public static final String FAILED_DATA_TYPE_MISMATCH = "failed: data type mismatch";
   public static final String FAILED_PROPERTY_IS_MANDATORY = "failed: property is mandatory";
   public static final String PASS_TOKEN = "pass";
   private static final Logger log = LoggerFactory.getLogger( VerifyJsonProperties.class );
   private static final String PROPERTY_NULL_VALUE = "isNullProperty";
   private String currentObject;
   private final String sourceObject;
   @SuppressWarnings( "unused" ) private String propertyDescription;
   private Boolean propertyMandatory;
   private String propertySelector;
   private PropertyDataTypes propertyType;
   private String propertyValue;
   private Boolean propertyValueNotNull;
   private String returnMessage = "pass";

   // constructors
   public VerifyJsonProperties( String sourceObject ) {
      this.sourceObject = sourceObject;
   }

   // public accessors and mutators
   public void reset() {
      this.currentObject = null;
      this.propertyDescription = null;
      this.propertyMandatory = null;
      this.propertyMandatory = null;
      this.propertySelector = null;
      this.propertyValue = null;
      this.propertyValueNotNull = null;
      this.returnMessage = null;
   }

   public String verifyProperty() {
      for( String currentObject : this.parseSourceToArray() ){
         this.currentObject = currentObject;
         determineCurrentProperty();
         verifySingleObjectProperty();
      }

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
         DocumentContext document = JsonPath.parse( this.currentObject );
         Object property = document.read( this.propertySelector );
         if( property != null )
            this.propertyValue = property.toString();
         else
            this.propertyValue = PROPERTY_NULL_VALUE;
      }catch( PathNotFoundException e ){
         log.info( "Selecting property: " + this.propertySelector + " failed." );
      }
   }

   private void parseSourceArray( List<String> sourceArray, JsonReader jsonReader ) {
      JsonArray jsonArray = null;
      jsonArray = jsonReader.readArray();
      for( javax.json.JsonValue jsonValue : jsonArray ){
         sourceArray.add( jsonValue.toString() );
      }
   }

   private void parseSourceObject( List<String> sourceArray, JsonReader jsonReader ) {
      sourceArray.add( this.sourceObject );
   }

   private List<String> parseSourceToArray() {
      List<String> sourceArray = Lists.newArrayList();
      JsonReader jsonReader = Json.createReader( new StringReader( StringUtils.replace( this.sourceObject, "'", "\"" ) ) );
      try{
         parseSourceArray( sourceArray, jsonReader );
      }catch( JsonParsingException e ){
         parseSourceObject( sourceArray, jsonReader );
      }finally{
         jsonReader.close();
      }

      return sourceArray;
   }

   private void verifyPropertyName() {
      log.debug( "Verify property: " + this.propertyValue );
      String result = PASS_TOKEN;
      if( this.propertyMandatory != null && this.propertyMandatory ){
         if( this.propertyValue != null ){
            result = PASS_TOKEN;
         }else{
            result = FAILED_PROPERTY_IS_MANDATORY;
         }
      }

      combineReturnMessage( result );
   }

   private void verifyPropertyType() {
      String result = PASS_TOKEN;

      if( this.propertyType != null && this.propertyValue != null ){
         PropertyValueVerifier valueVerifier = new PropertyValueVerifier( this.propertyValue );
         if( !valueVerifier.isTypeOf( this.propertyType ) ){
            result = FAILED_DATA_TYPE_MISMATCH;
         }
      }
      combineReturnMessage( result );
   }

   private void verifyMandatoryProperty() {
      String result;
      if( (this.propertyMandatory && this.propertyValue != null) || !this.propertyMandatory ){
         result = PASS_TOKEN;
      }else{
         result = FAILED_PROPERTY_IS_MANDATORY;
      }

      combineReturnMessage( result );
   }

   private void verifyPropertyValueNotNull() {
      String result = PASS_TOKEN;
      if( this.propertyValueNotNull ){
         if( this.propertyValue == null || this.propertyValue.equals( PROPERTY_NULL_VALUE ) ){
            result = FAILED_PROPERTY_VALUE_NOT_NULL;
         }
      }

      combineReturnMessage( result );
   }

   private void verifySingleObjectProperty() {
      verifyPropertyName();

      if( this.propertyValue != null ){
         if( this.propertyType != null )
            verifyPropertyType();
         if( this.propertyMandatory != null )
            verifyMandatoryProperty();
         if( this.propertyValueNotNull != null )
            verifyPropertyValueNotNull();
      }
   }
}