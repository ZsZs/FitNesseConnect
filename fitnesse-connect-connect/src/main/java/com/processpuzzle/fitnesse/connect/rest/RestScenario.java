package com.processpuzzle.fitnesse.connect.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class RestScenario {
   protected ApplicationConfiguration configuration;
   protected String resourcePath;
   protected RestClient restClient;
   protected List<SessionResults<?>> sessionResults = Lists.newArrayList();
   protected long sessionEnded;
   protected long sessionStarted;
   private ResponseEntity<String> lastResponse;

   // constructors
   public RestScenario( String configurationName, String resourcePath ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), resourcePath );
   }
   
   public RestScenario( ApplicationConfiguration configuration, String resourcePath ) {
      this.configuration = configuration;
      this.resourcePath = resourcePath;
      instantiateRestClient( this.resourcePath );
   }

   protected RestScenario() {
      instantiateRestClient( "" );
   }

   // public accessors and mutators
   public void getResource( String resourcePath ){
      lastResponse = restClient.getResource( resourcePath, String.class, null );
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
   
   public String responseBody(){
      return lastResponse.getBody();
   }
   
   public int responseStatus(){
      return lastResponse.getStatusCodeValue();
   }

   public long responseTime() {
      long responseTime = 0;

      for( SessionResults<?> sessionResult : sessionResults ){
         if( sessionResult.getResponseTime() > responseTime ){
            responseTime = sessionResult.getResponseTime();
         }
      }
      return responseTime / 1000;
   }

   public String sessionIdsForAllUsers() {
      String returnValue = "";

      for( SessionResults<?> sessionResult : sessionResults ){
         returnValue += sessionResult.getSessionId() + ";\n";
      }

      return returnValue;
   }

   public String sessionIdForUser( String userName ) {
      for( SessionResults<?> loginResults : sessionResults ){
         if( loginResults.getException() == null && loginResults.getUserName() != null && loginResults.getUserName().equals( userName ) ){
            return loginResults.getSessionId();
         }
      }
      return null;
   }

   // properties
   // @formatter:off
   public RestClient getRestClient() { return restClient; }
   // @formatter:on

   // protected, private helper methods
   protected void instantiateRestClient( final String resourceURI ) {
      restClient = configuration.createRestClient( resourceURI );
   }

   protected void instantiateSslRestClient( final String resourceURI, final String certificateName ) throws Exception {
      restClient = configuration.createSslRestClient( resourceURI, certificateName );
   }
}
