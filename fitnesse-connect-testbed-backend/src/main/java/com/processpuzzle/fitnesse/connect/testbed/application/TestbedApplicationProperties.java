package com.processpuzzle.fitnesse.connect.testbed.application;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

@ConfigurationProperties( prefix = "testbed-application" )
public class TestbedApplicationProperties {
   private String dateTimeFormatter;
   private Map<String, String> users = Maps.newHashMap(); 

   // public accessors and mutators
   
   // properties
   // @formatter:off
   public DateTimeFormatter getDateTimeFormatter() { return DateTimeFormatter.ofPattern( dateTimeFormatter ); }
   public String getUser( String userName ) { return users.get( userName ); }
   public Map<String, String> getUsers() { return users; }
   public void setDateTimeFormatter( String dateTimeFormatter ) { this.dateTimeFormatter = dateTimeFormatter; }
   public void setUsers( Map<String, String> users ) { this.users = users; }
   // @formatter:on
}
