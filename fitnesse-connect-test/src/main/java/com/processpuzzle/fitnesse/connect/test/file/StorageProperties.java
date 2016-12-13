package com.processpuzzle.fitnesse.connect.test.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( "storage" )
public class StorageProperties {
   private String location = "upload-dir";

   // properties
   // @formatter:off
   public String getLocation() { return location; }
   public void setLocation( String location ) { this.location = location; }
   // @formatter:on

}
