package com.processpuzzle.fitnesse.connect.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;

public abstract class RestConnector {
   private static final Logger logger = LoggerFactory.getLogger( RestConnector.class );
   protected ApplicationConfiguration configuration;
   protected final String host;
   protected String resourcePath;
   protected RestClient restClient;
   protected List<SessionResults<?>> sessionResults = Lists.newArrayList();
   protected long sessionEnded;
   protected long sessionStarted;
   protected ResponseEntity<String> lastResponse;

   protected RestConnector( ApplicationConfiguration configuration, String resourcePath ) {
      this.configuration = configuration;
      this.host = configuration.buildHostUrl();
      this.resourcePath = resourcePath;
      instantiateRestClient();
   }

   // public accessors and mutators
   public void getResource( String resourceUri ) {
      String resourceURL = compileResourceUrl( resourceUri );
      try{
         lastResponse = restClient.getResource( resourceURL, String.class, null );
      }catch( HttpClientErrorException e ){
         logger.debug( "Retrieving the resource: " + resourceURL + " resulted in exception." );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
   }

   public void getResource( String resourceUri, String notUsedComment ) {
      getResource( resourceUri );
   }

   public String responseBody() {
      return lastResponse.getBody();
   }

   public String responseBody( String notUsedComment ) {
      return responseBody();
   }

   public String responseHeader( String headerKey ) {
      for( Map.Entry<String, List<String>> headerEntry : lastResponse.getHeaders().entrySet() ){
         if( headerEntry.getKey().equals( headerKey )) return headerEntry.getValue().toString(); 
      }
      
      return "";
   }

   public String responseHeaders() {
      String headersText = "";
      for( Map.Entry<String, List<String>> headerEntry : lastResponse.getHeaders().entrySet() ){
         headersText += headerEntry.toString() + "\n"; 
      }
      return headersText;
   }

   public int responseStatus() {
      return lastResponse.getStatusCodeValue();
   }

   public int responseStatus( String notUsedComment ) {
      return responseStatus();
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

   // @formatter:off
   public RestClient getRestClient() { return restClient; }
   // @formatter:on

   // protected private helper methods
   protected String compileResourceUrl( String resourceURI ) {
      String resourceUrl = this.host;
      try{
         resourceUrl = new URI( this.host ).toString();
         if( this.resourcePath != null ){
            resourceUrl += "/" + StringUtils.stripStart( this.resourcePath, "/" );
         }
         if( resourceURI != null ){
            resourceUrl += "/" + StringUtils.stripStart( resourceURI, "/" );
         }
      }catch( URISyntaxException e ){
         logger.error( "Couldn't compile full URL from host: " + this.host + " and resource path: " + this.resourcePath  + " and resource uri: " + resourceURI );;
      }
      return resourceUrl;
   }

   protected void instantiateRestClient() {
      restClient = configuration.createRestClient();
   }

   protected void instantiateSslRestClient( final String resourceURI, final String certificateName ) throws Exception {
      restClient = configuration.createSslRestClient( resourceURI, certificateName );
   }
}
