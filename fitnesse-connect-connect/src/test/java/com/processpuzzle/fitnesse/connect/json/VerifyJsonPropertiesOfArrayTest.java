package com.processpuzzle.fitnesse.connect.json;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class VerifyJsonPropertiesOfArrayTest {
   private static final String JSON_OBJECT = "[{ 'text': 'Hello World', 'numeric': '2017' }, { 'text': 'Hello Zsolt', 'numeric': '1960' }]";
   private VerifyJsonProperties verifyJson;

   @Before public void beforeEachTest() throws Exception {
      verifyJson = new VerifyJsonProperties( JSON_OBJECT );
   }

   @Test public void verifyProperty_whenPropertyExist_returnsPass() {
      verifyJson.setProperty( "$['text']" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), equalTo( VerifyJsonProperties.PASS_TOKEN ) );
   }

   @Test public void verifyProperty_whenPropertyNotExistAndIsMandatory_returnsFailed() {
      verifyJson.setProperty( "$['No property']" );
      verifyJson.setIsMandatory( "yes" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), containsString( VerifyJsonProperties.FAILED_PROPERTY_IS_MANDATORY ));
   }

   @Test public void verifyColumn_whenDataTypeMatches_returnsPass() {
      verifyJson.setProperty( "$['text']" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), equalTo( VerifyJsonProperties.PASS_TOKEN ) );

      verifyJson.setProperty( "$['numeric']" );
      verifyJson.setDataType( "Integer" );
      assertThat( verifyJson.verifyProperty(), equalTo( VerifyJsonProperties.PASS_TOKEN ) );
   }

}
