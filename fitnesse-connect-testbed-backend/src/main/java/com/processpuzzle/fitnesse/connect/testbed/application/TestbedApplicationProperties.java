package com.processpuzzle.fitnesse.connect.testbed.application;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "testbed-application" )
public class TestbedApplicationProperties {
   private String dateTimeFormatter;

   // public accessors and mutators
   
   // properties
   // @formatter:off
   public DateTimeFormatter getDateTimeFormatter() { return DateTimeFormatter.ofPattern( dateTimeFormatter ); }
   public void setDateTimeFormatter( String dateTimeFormatter ) { this.dateTimeFormatter = dateTimeFormatter; }
   // @formatter:on
}
