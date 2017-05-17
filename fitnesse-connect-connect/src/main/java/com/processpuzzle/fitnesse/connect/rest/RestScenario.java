package com.processpuzzle.fitnesse.connect.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class RestScenario extends RestConnector {
   static final Logger logger = LoggerFactory.getLogger( RestScenario.class );
   // constructors
   public RestScenario( String configurationName, String resourcePath ) {
      super( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), resourcePath );
   }
   
   public boolean maximumResponseTime( int maximumResponseTime ) {
      return responseTime() <= maximumResponseTime;
   }

   public int numberOfLoggedInUsersIs() {
      int numberOfLoggedInUsers = 0;
      for( SessionResults<?> sessionResult : sessionResults ){
         if( sessionResult.getSessionId() != null && !sessionResult.getSessionId().isEmpty() ){
            numberOfLoggedInUsers++;
         }
      }

      return numberOfLoggedInUsers;
   }
}
