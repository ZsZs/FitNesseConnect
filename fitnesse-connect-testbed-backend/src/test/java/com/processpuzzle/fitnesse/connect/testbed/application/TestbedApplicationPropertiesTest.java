package com.processpuzzle.fitnesse.connect.testbed.application;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { TestbedApplicationProperties.class })
@EnableConfigurationProperties
public class TestbedApplicationPropertiesTest {
   private static final String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";
   @Autowired TestbedApplicationProperties testbedProperties;
   
   @Test public void instantiationLoadsProperties(){
      assertThat( testbedProperties.getDateTimeFormatter(), samePropertyValuesAs( DateTimeFormatter.ofPattern( DATE_TIME_FORMATTER_PATTERN )));
   }
      
   @Test public void instantiation_loadsUsers(){
      assertThat( testbedProperties.getUsers().entrySet().size(), greaterThanOrEqualTo( 2 ));
      assertThat( testbedProperties.getUser( "rest-client" ), equalTo( "password" ));
      assertThat( testbedProperties.getUser( "user" ), equalTo( "password" ));
   }
}
