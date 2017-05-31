package com.processpuzzle.fitnesse.connect.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;

public abstract class RestConnector {
   private static final Logger logger = LoggerFactory.getLogger( RestConnector.class );
   protected ApplicationConfiguration configuration;
   protected final String host;
   protected ResponseEntity<String> lastResponse;
   protected String requestBody;
   protected HttpHeaders requestHeaders = new HttpHeaders();
   protected String resourcePath;
   protected RestClient restClient;
   protected List<SessionResults<?>> sessionResults = Lists.newArrayList();
   protected long sessionEnded;
   protected long sessionStarted;

   protected RestConnector( ApplicationConfiguration configuration, String resourcePath ) {
      this.configuration = configuration;
      this.host = configuration.buildHostUrl();
      this.resourcePath = resourcePath;
      instantiateRestClient();
   }

   // public accessors and mutators
   public void addRequestHeader( String headerName, String headerValue ) {
      logger.debug( "Adding request header: " + headerName + ", " + headerValue );
      this.requestHeaders.put( headerName, convertToList( headerValue ) );
   }

   public void deleteResource() {
      deleteResource( null );
   }

   public void deleteResource( String resourceUri ) {
      String resourceURL = compileResourceUrl( resourceUri );
      logger.debug( "DELETE resource: " + resourceURL );
      try{
         lastResponse = restClient.deleteResource( resourceURL, requestHeaders, null );
      }catch( HttpClientErrorException e ){
         logger.debug( "Retrieving the resource: " + resourceURL + " resulted in exception." );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
   }

   public void getResource() {
      getResource( null );
   }

   public void getResource( String resourceUri ) {
      String resourceURL = compileResourceUrl( resourceUri );
      logger.debug( "GET resource: " + resourceURL );
      try{
         lastResponse = restClient.getResource( resourceURL, requestHeaders, String.class, null );
      }catch( HttpClientErrorException e ){
         logger.debug( "Retrieving the resource: " + resourceURL + " resulted in HttpClientErrorException: " + e.getMessage() );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
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

   public void patchResource() {
      patchResource( null, requestBody );
   }

   public void patchResource( String resourceUri, String resourceObject ) {
      String resourceURL = compileResourceUrl( resourceUri );
      logger.debug( "PATCH resource: " + resourceURL );
      try{
         lastResponse = restClient.patchResource( resourceURL, resourceObject );
      }catch( HttpClientErrorException e ){
         logger.debug( "Patching resource: " + resourceURL + " resulted in exception." );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
   }

   public void postResource() {
      postResource( null, requestBody );
   }

   public void postResource( String resourceUri, String resourceObject ) {
      String resourceURL = compileResourceUrl( resourceUri );
      logger.debug( "POST resource: " + resourceObject + " to: " + resourceURL );
      try{
         lastResponse = restClient.postResource( resourceURL, resourceObject );
      }catch( HttpClientErrorException e ){
         logger.debug( "Posting resource: " + resourceURL + " resulted in exception." );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
   }

   public void putResource() {
      putResource( null, requestBody );
   }

   public void putResource( String resourceUri, String resourceObject ) {
      String resourceURL = compileResourceUrl( resourceUri );
      logger.debug( "PUT resource: " + resourceObject + " to: " + resourceURL );
      try{
         lastResponse = restClient.putResource( resourceURL, resourceObject );
      }catch( HttpClientErrorException e ){
         logger.debug( "Putting resource: " + resourceURL + " resulted in exception." );
         lastResponse = new ResponseEntity<String>( e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode() );
      }
   }

   public String responseBody() {
      return lastResponse.getBody();
   }

   public String responseBody( String notUsedComment ) {
      return responseBody();
   }

   public Object responseBodyProperty( String jsonPath ) {
      Object propertyValue = null;
      String jsonString = this.responseBody();
      try{
         DocumentContext jsonContext = JsonPath.parse( jsonString );
         propertyValue = jsonContext.read( jsonPath );
      }catch( PathNotFoundException e ){
         propertyValue = e.getLocalizedMessage();
         logger.debug( "Selecting property: " + jsonPath + " failed.", e );
      }

      return propertyValue;
   }

   public String responseHeader( String headerKey ) {
      logger.trace( "Searching response header by key: " + headerKey );
      for( Map.Entry<String, List<String>> headerEntry : lastResponse.getHeaders().entrySet() ){
         if( headerEntry.getKey().equals( headerKey ) )
            return headerEntry.getValue().toString();
      }

      logger.trace( "Key not found in response headers" );
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

   public void setRequestBody( String requestBody ) {
      logger.debug( "Define request body as: " + requestBody );
      this.requestBody = stripPreTags( requestBody );
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
         logger.error( "Couldn't compile full URL from host: " + this.host + " and resource path: " + this.resourcePath + " and resource uri: " + resourceURI );
         ;
      }
      return resourceUrl;
   }

   protected List<String> convertToList( String value ) {
      Stream<String> elements = Stream.of( value.split( ";" ) );
      return elements.map( String::trim ).collect( Collectors.toList() );
   }

   protected void instantiateRestClient() {
      restClient = configuration.createRestClient();
   }

   protected void instantiateSslRestClient( final String resourceURI, final String certificateName ) throws Exception {
      restClient = configuration.createSslRestClient( resourceURI, certificateName );
   }

   private String stripPreTags( String requestBody ) {
      String strippedBody = StringUtils.remove( requestBody, "<pre>" );
      strippedBody = StringUtils.remove( strippedBody, "</pre>" );
      return strippedBody;
   }
}
