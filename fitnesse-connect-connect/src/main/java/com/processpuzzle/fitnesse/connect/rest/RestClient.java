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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClient {
   private static final Logger logger = LoggerFactory.getLogger( RestClient.class );
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

   public <T> ResponseEntity<T> postResource( String resourceURI, T resourceObject, Class<T> resourceClass ) throws RestClientException {
      return postResource( resourceURI, resourceObject, resourceClass, null );
   }

   public <T> ResponseEntity<T> postResource( String resourcePath, T resourceObject, Class<T> resourceClass, String sessionId ) throws RestClientException {
      return postResource( resourcePath, null, resourceObject, resourceClass, sessionId );
   }
   
   public <T> ResponseEntity<T> postResource( String resourcePath, HttpHeaders requestHeaders, T resourceObject, Class<T> resourceClass, String sessionId ) throws RestClientException {
      logger.info( "Posting: " + resourceObject.toString() );

      HttpHeaders headers = createHeaderWithSessionId( requestHeaders, sessionId );
      HttpEntity<T> request = new HttpEntity<T>( resourceObject, headers );
      ResponseEntity<T> response = null;
      try{
         response = restTemplate.exchange( resourcePath, HttpMethod.POST, request, resourceClass );
         if( !response.getStatusCode().is2xxSuccessful() ){
            throw new RestClientException( resourcePath, HttpMethod.POST.name(), resourceObject.toString() );
         }

         if( response.getBody() != null ){
            logger.info( "Response: " + response.getBody().toString() );
         }
      }catch( Exception e ){
         throw new RestClientException( resourcePath, HttpMethod.POST.name(), resourceObject.toString(), e );
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
      
      if( requestHeaders == null ) headers = new HttpHeaders();
      else headers = requestHeaders;
      
      headers.setContentType( MediaType.APPLICATION_JSON );
      headers.set( "Cookie", sessionId );
      return headers;
   }
   
   protected void createRestTemplate() {
      restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add( 0, new StringHttpMessageConverter( Charset.forName( "UTF-8" ) ) );
   }
}
