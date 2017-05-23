package com.processpuzzle.fitnesse.connect.rest;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClient {
   private static final Logger logger = LoggerFactory.getLogger( RestClient.class );
   private static final int TIMEOUT = 5000;
   private SimpleClientHttpRequestFactory clientHttpRequestFactory;
   protected RestTemplate restTemplate;

   // constructors
   public static RestClient create() {
      RestClient restClient = new RestClient();

      return restClient;
   }

   protected RestClient() {
      configureProxy();
      createRestTemplate();
   }

   // public accessors and mutators
   public ResponseEntity<String> deleteResource( String resourceURI ) throws RestClientException {
      return deleteResource( resourceURI, null );
   }

   public ResponseEntity<String> deleteResource( String resourcePath, String sessionId ) throws RestClientException {
      return deleteResource( resourcePath, null, sessionId );
   }

   public ResponseEntity<String> deleteResource( String resourcePath, HttpHeaders requestHeaders, String sessionId ) {
      logger.info( "Deleting: " + resourcePath );

      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<String> request = new HttpEntity<String>( "dummy request", headers );
      ResponseEntity<String> response = null;
      response = restTemplate.exchange( resourcePath, HttpMethod.DELETE, request, String.class );

      return response;
   }

   public <T> ResponseEntity<T> getResource( String resourcePath, Class<T> resourceClass, String sessionId ) {
      return getResource( resourcePath, null, resourceClass, sessionId );
   }

   public <T> ResponseEntity<T> getResource( String resourcePath, HttpHeaders requestHeaders, Class<T> resourceClass, String sessionId ) {
      logger.info( "GET resource: " + resourcePath );
      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<T> request = new HttpEntity<T>( null, headers );
      ResponseEntity<T> response = restTemplate.exchange( resourcePath, HttpMethod.GET, request, resourceClass );
      return response;
   }

   public HttpHeaders getHttpHeaders( String resourcePath ) {
      HttpHeaders httpHeaders = restTemplate.headForHeaders( resourcePath );
      logger.info( httpHeaders.toString() );

      return httpHeaders;
   }

   public ResponseEntity<String> patchResource( String resourceURI, String resourceObject ) {
      return patchResource( resourceURI, resourceObject, String.class, null );
   }

   public <T> ResponseEntity<T> patchResource( String resourceURI, T resourceObject, Class<T> resourceClass ) {
      return patchResource( resourceURI, resourceObject, resourceClass, null );
   }

   public <T> ResponseEntity<T> patchResource( String resourcePath, T resourceObject, Class<T> resourceClass, String sessionId ) {
      return patchResource( resourcePath, null, resourceObject, resourceClass, sessionId );
   }

   public <T> ResponseEntity<T> patchResource( String resourcePath, HttpHeaders requestHeaders, T resourceObject, Class<T> resourceClass, String sessionId ) {
      logger.info( "Patching: " + resourcePath );

      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<T> request = new HttpEntity<T>( resourceObject, headers );
      ResponseEntity<T> response = restTemplate.exchange( resourcePath, HttpMethod.PATCH, request, resourceClass );

      if( response.getBody() != null ){
         logger.debug( "Response: " + response.getBody().toString() );
      }

      return response;
   }

   public ResponseEntity<String> postResource( String resourceURI, String resourceObject ) {
      return postResource( resourceURI, resourceObject, String.class, null );
   }

   public <T> ResponseEntity<T> postResource( String resourceURI, T resourceObject, Class<T> resourceClass ) {
      return postResource( resourceURI, resourceObject, resourceClass, null );
   }

   public <T> ResponseEntity<T> postResource( String resourcePath, T resourceObject, Class<T> resourceClass, String sessionId ) {
      return postResource( resourcePath, null, resourceObject, resourceClass, sessionId );
   }

   public <T> ResponseEntity<T> postResource( String resourcePath, HttpHeaders requestHeaders, T resourceObject, Class<T> resourceClass, String sessionId ) {
      logger.info( "Posting: " + resourceObject.toString() );

      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<T> request = new HttpEntity<T>( resourceObject, headers );
      ResponseEntity<T> response = restTemplate.exchange( resourcePath, HttpMethod.POST, request, resourceClass );

      if( response.getBody() != null ){
         logger.debug( "Response: " + response.getBody().toString() );
      }

      return response;
   }

   public ResponseEntity<String> putResource( String resourceURI, String resourceObject ) {
      return putResource( resourceURI, resourceObject, String.class, null );
   }

   public <T> ResponseEntity<T> putResource( String resourceURI, T resourceObject, Class<T> resourceClass ) {
      return putResource( resourceURI, resourceObject, resourceClass, null );
   }

   public <T> ResponseEntity<T> putResource( String resourcePath, T resourceObject, Class<T> resourceClass, String sessionId ) {
      return putResource( resourcePath, null, resourceObject, resourceClass, sessionId );
   }

   public <T> ResponseEntity<T> putResource( String resourcePath, HttpHeaders requestHeaders, T resourceObject, Class<T> resourceClass, String sessionId ) {
      logger.info( "Putting: " + resourceObject.toString() );

      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<T> request = new HttpEntity<T>( resourceObject, headers );
      ResponseEntity<T> response = restTemplate.exchange( resourcePath, HttpMethod.PUT, request, resourceClass );

      if( response.getBody() != null ){
         logger.debug( "Response: " + response.getBody().toString() );
      }

      return response;
   }

   public <T> String retrieveSessionId( ResponseEntity<T> response ) {
      String cookies = response.getHeaders().get( "Set-Cookie" ).get( 0 );
      String sessionId = StringUtils.substringBefore( cookies, ";" );

      return sessionId;
   }

   // properties
   // @formatter:off
   public RestTemplate getRestTemplate() { return restTemplate; }
   // @formatter:on

   // protected, private helper methods
   private void configureProxy() {
      clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
      Proxy proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress( "proxyfarm-fth.inac.siemens.net", 84 ) );
      clientHttpRequestFactory.setProxy( proxy );
   }

   private HttpHeaders createHeaderWithSessionId( HttpHeaders requestHeaders, String sessionId ) {
      HttpHeaders headers;

      if( requestHeaders == null )
         headers = new HttpHeaders();
      else
         headers = requestHeaders;

      headers.setContentType( MediaType.APPLICATION_JSON );
      headers.set( "Cookie", sessionId );
      return headers;
   }

   protected void createRestTemplate() {
      restTemplate = new RestTemplate();
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      requestFactory.setConnectTimeout(TIMEOUT);
      requestFactory.setReadTimeout(TIMEOUT);

      restTemplate.setRequestFactory(requestFactory);
      restTemplate.getMessageConverters().add( 0, new StringHttpMessageConverter( Charset.forName( "UTF-8" ) ) );
   }
}
