package com.processpuzzle.fitnesse.connect.json;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PropertyValueVerifierTest {

   @Test public void isTypeOfString_whenValueIsString_returnsTrue(){
      assertThat( new PropertyValueVerifier( "Hello World" ).isTypeOfString(), is( true ));
   }
   
   @Test public void isTypeOfString_whenValueIsInteger_returnsTrue(){
      assertThat( new PropertyValueVerifier( "1234" ).isTypeOfString(), is( true ));
   }
   
   @Test public void isTypeOfInteger_whenValueIsInteger_returnsTrue(){
      assertThat( new PropertyValueVerifier( "1234" ).isTypeOfInteger(), is( true ));
   }
   
   @Test public void isTypeOfInteger_whenValueIsNotInteger_returnsFalse(){
      assertThat( new PropertyValueVerifier( "Hello" ).isTypeOfInteger(), is( false ));
   }
   
   @Test public void isTypeOfInteger_whenValueIsFloat_returnsFalse(){
      assertThat( new PropertyValueVerifier( "1234.0123" ).isTypeOfInteger(), is( false ));
   }
   
   @Test public void isTypeOfFloat_whenValueIsFloat_returnsTrue(){
      assertThat( new PropertyValueVerifier( "1234.0123" ).isTypeOfFloat(), is( true ));
   }
   
   @Test public void isTypeOfFloat_whenValueIsInteger_returnsTrue(){
      assertThat( new PropertyValueVerifier( "1234" ).isTypeOfFloat(), is( true ));
   }
   
   @Test public void isTypeOfFloat_whenValueIsNotNumber_returnsFalse(){
      assertThat( new PropertyValueVerifier( "Hello" ).isTypeOfFloat(), is( false ));
   }
   
   @Test public void isTypeOfDate_whenValueIsDate_returnsTrue(){
      assertThat( new PropertyValueVerifier( "2017.03.23" ).isTypeOfDate(), is( true ));
   }
   
   @Test public void isTypeOfDate_whenValueIsText_returnsFalse(){
      assertThat( new PropertyValueVerifier( "Hello" ).isTypeOfDate(), is( false ));
   }
   
   @Test public void isTypeOfBoolean_whenValueIsYes_returnsTrue(){
      assertThat( new PropertyValueVerifier( "yes" ).isTypeOfBoolean(), is( true ));
   }
   
   @Test public void isTypeOfBoolean_whenValueIsTrue_returnsTrue(){
      assertThat( new PropertyValueVerifier( "true" ).isTypeOfBoolean(), is( true ));
   }
   
   @Test public void isTypeOfBoolean_whenValueIsNo_returnsTrue(){
      assertThat( new PropertyValueVerifier( "no" ).isTypeOfBoolean(), is( true ));
   }
   
   @Test public void isTypeOfBoolean_whenValueIsFalse_returnsTrue(){
      assertThat( new PropertyValueVerifier( "false" ).isTypeOfBoolean(), is( true ));
   }
   
   @Test public void isTypeOfBoolean_whenValueIsText_returnsFalse(){
      assertThat( new PropertyValueVerifier( "Hello" ).isTypeOfBoolean(), is( false ));
   }
}
