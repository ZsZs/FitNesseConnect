package com.processpuzzle.fitnesse.connect.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class VerifyJsonPropertiesTest {
   private static final String JSON_OBJECT = "{ 'text': 'Hello World', 'numeric': '2017', 'date': '2017/03/21', 'boolean': 'yes', 'optional': 'optional' }";
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

   /*
    * @Test public void verifyColumn_whenDataTypeMatches_returnsPass(){ verifyJson.setProperty( "String Column" ); verifyJson.setDataType( "String" );
    * assertThat( verifyJson.verifyProperty(), equalTo( "pass" )); verifyJson.setColumn( "Integer Column" ); verifyJson.setDataType( "Integer" ); assertThat(
    * verifyJson.verifyProperty(), equalTo( "pass" )); verifyJson.setColumn( "Date Column" ); verifyJson.setDataType( "Date" ); assertThat(
    * verifyJson.verifyProperty(), equalTo( "pass" )); }
    * @Test public void verifyColumn_whenColumnIsMandatory_returnsPass(){ verifyJson.setColumn( "Optional Column" ); verifyJson.setDataType( "String" );
    * verifyJson.setIsMandatory( "no" ); verifyJson.setIsValueMandatory( "yes" ); assertThat( verifyJson.verifyProperty(), equalTo( "pass" )); }
    * @Test public void verifyColumn_whenColumnValueIsMandatory_returnsPass(){ verifyJson.setColumn( "Optional Value Column" ); verifyJson.setDataType( "String"
    * ); verifyJson.setIsMandatory( "yes" ); verifyJson.setIsValueMandatory( "no" ); assertThat( verifyJson.verifyColumn(), equalTo( "pass" )); }
    */
}
