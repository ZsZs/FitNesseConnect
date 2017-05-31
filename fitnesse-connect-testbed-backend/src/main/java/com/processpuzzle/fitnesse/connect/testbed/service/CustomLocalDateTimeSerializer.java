package com.processpuzzle.fitnesse.connect.testbed.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.processpuzzle.fitnesse.connect.testbed.application.TestbedApplicationProperties;

@Component
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> implements ApplicationContextAware {
   private static final long serialVersionUID = 2200563743021439876L;
   private static ApplicationContext applicationContext;
   private TestbedApplicationProperties testbedProperties;

   public CustomLocalDateTimeSerializer() {
      this( null );
   }

   public CustomLocalDateTimeSerializer( Class<LocalDateTime> t ) {
      super( t );
      retrieveTestbedProperties();
   }

   @Override public void serialize( LocalDateTime value, JsonGenerator generator, SerializerProvider arg2 ) throws IOException, JsonProcessingException {
      generator.writeString( testbedProperties.getDateTimeFormatter().format( value ) );
   }
   
   // properties
   // @formatter:off
   @Override public void setApplicationContext( ApplicationContext context ) throws BeansException {  applicationContext = context; }
   // @formatter:on
   
   // protected, private helper methods
   private void retrieveTestbedProperties() {
      if( CustomLocalDateTimeSerializer.applicationContext != null ){
         this.testbedProperties = applicationContext.getBean( TestbedApplicationProperties.class );
      }
   }
}
