package com.processpuzzle.fitnesse.connect.testbed.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.processpuzzle.fitnesse.connect.testbed.application.TestbedApplicationProperties;

@Component
public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> implements ApplicationContextAware {
   private static final long serialVersionUID = 4944724675112590154L;
   private static ApplicationContext applicationContext;
   private TestbedApplicationProperties testbedProperties;

   public CustomLocalDateTimeDeserializer() {
      this( null );
   }

   public CustomLocalDateTimeDeserializer( Class<?> vc ) {
      super( vc );
      retrieveTestbedProperties();
   }

   @Override public LocalDateTime deserialize( JsonParser jsonparser, DeserializationContext context ) throws IOException, JsonProcessingException {
      String date = jsonparser.getText();
      return LocalDateTime.parse( date, testbedProperties.getDateTimeFormatter() );
   }
   
   // properties
   // @formatter:off
   @Override public void setApplicationContext( ApplicationContext context ) throws BeansException {  applicationContext = context; }
   // @formatter:on
   
   // protected, private helper methods
   private void retrieveTestbedProperties() {
      if( CustomLocalDateTimeDeserializer.applicationContext != null ){
         this.testbedProperties = applicationContext.getBean( TestbedApplicationProperties.class );
      }
   }
}
