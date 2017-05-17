package com.processpuzzle.fitnesse.connect.rest;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
   private static final Logger logger = LoggerFactory.getLogger( RestClient.class );
   private SimpleClientHttpRequestFactory clientHttpRequestFactory;
   @Autowired protected RestTemplate restTemplate;

   // constructors
   protected RestClient(){
      configureProxy();
   }

   // public accessors and mutators
   public <T> ResponseEntity<T> getResource( String resourcePath, Class<T> resourceClass, String sessionId ) {
      HttpHeaders headers = createHeaderWithSessionId( sessionId );

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
      logger.info( "Posting: " + resourceObject.toString() );

      HttpHeaders headers = createHeaderWithSessionId( sessionId );
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
   // @formatter:on

   // protected, private helper methods
   private void configureProxy() {
      clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
      Proxy proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress( "proxyfarm-fth.inac.siemens.net", 84 ) );
      clientHttpRequestFactory.setProxy( proxy );
   }
   
   private ClientHttpRequestFactory createClientHttpRequestFactory() {
      HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
      return clientHttpRequestFactory;
   }

   private HttpHeaders createHeaderWithSessionId( String sessionId ) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType( MediaType.APPLICATION_JSON );
      headers.set( "Cookie", sessionId );
      return headers;
   }
   
   @Bean public RestTemplate getRestTemplate() {
      RestTemplate restTemplate = new RestTemplate( createClientHttpRequestFactory() );
      restTemplate.getMessageConverters().add( 0, new StringHttpMessageConverter( Charset.forName( "UTF-8" ) ) );

      return  restTemplate; 
   }
}
