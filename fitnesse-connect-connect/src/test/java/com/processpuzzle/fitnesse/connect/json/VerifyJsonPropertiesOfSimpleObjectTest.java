package com.processpuzzle.fitnesse.connect.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class VerifyJsonPropertiesOfSimpleObjectTest {
   private static final String JSON_OBJECT = "{ 'text': 'Hello World', 'numeric': '2017', 'date': '2017.03.21', 'boolean': 'yes', 'optional': 'optional' }";
   private VerifyJsonProperties verifyJson;

   @Before public void beforeEachTest() throws Exception {
      verifyJson = new VerifyJsonProperties( JSON_OBJECT );
   }

   @Test public void verifyProperty_whenPropertyExist_returnsPass() {
      verifyJson.setProperty( "$['text']" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );
   }

   @Test public void verifyProperty_whenPropertyNotExistAndIsMandatory_returnsFailed() {
      verifyJson.setProperty( "$['No property']" );
      verifyJson.setIsMandatory( "yes" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), equalTo( "failed: property not defined" ) );
   }

   @Test public void verifyColumn_whenDataTypeMatches_returnsPass() {
      verifyJson.setProperty( "$['text']" );
      verifyJson.setDataType( "String" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );

      verifyJson.setProperty( "$['numeric']" );
      verifyJson.setDataType( "Integer" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );

      verifyJson.setProperty( "$['date']" );
      verifyJson.setDataType( "Date" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );

      verifyJson.setProperty( "$['boolean']" );
      verifyJson.setDataType( "Boolean" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );
   }

   @Test public void verifyColumn_whenPropertyIsNotDate_returnsFailed() {
      verifyJson.setProperty( "$['text']" );
      verifyJson.setDataType( "Date" );
      assertThat( verifyJson.verifyProperty(), equalTo( "failed: data type mismatch" ) );
   }

   @Test public void verifyColumn_whenPropertyIsMandatory_returnsPass() {
      verifyJson.setProperty( "$['optional']" );
      verifyJson.setDataType( "String" );
      verifyJson.setIsMandatory( "no" );
      verifyJson.setNotNull( "yes" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );
   }

   @Test public void verifyColumn_whenProperyIsNotNull_returnsPass() {
      verifyJson.setProperty( "optional" );
      verifyJson.setDataType( "String" );
      verifyJson.setIsMandatory( "yes" );
      verifyJson.setNotNull( "no" );
      assertThat( verifyJson.verifyProperty(), equalTo( "pass" ) );
   }
}
