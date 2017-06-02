package com.processpuzzle.fitnesse.connect.database;

import java.text.MessageFormat;

public class UnknownApplicationConfigurationException extends RuntimeException {
   private static final long serialVersionUID = -7162800163178638769L;
   private static final String messageTemplate = "Application configuration {0} is unknown";
   private final String configurationName;

   public UnknownApplicationConfigurationException(){
      this( null );
   }
   
   public UnknownApplicationConfigurationException( String configurationName ){
      super( MessageFormat.format( messageTemplate, new Object[] { configurationName }));
      this.configurationName = configurationName;
   }
   
   // properties
   // @formatter:off
   public String getConfigurationName(){ return configurationName; }
   // @formatter:on
}
