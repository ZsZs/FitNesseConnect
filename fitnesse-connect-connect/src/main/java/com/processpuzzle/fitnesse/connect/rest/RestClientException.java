package com.processpuzzle.fitnesse.connect.rest;

import java.text.MessageFormat;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RestClientException extends Exception {
   private static final long serialVersionUID = 5880058073517527976L;
   private static final String messageTemplate = "Invoking {0} location with {1} method by {2} resource failed.";
   private HttpClientErrorException clientException;
   
   public RestClientException( HttpClientErrorException clientException ){
      super( clientException );
      this.clientException = clientException;
   }

   public RestClientException( String resourceURI, String httpMethod, String resource ){
      super( MessageFormat.format( messageTemplate, new Object[] { resourceURI, httpMethod, resource }));
   }
   
   public RestClientException( String resourceURI, String httpMethod, String resource, Throwable cause ){
      super( MessageFormat.format( messageTemplate, new Object[] { resourceURI, httpMethod, resource }), cause );
   }
   
   // properties
   // @formatter:off
   public String getResponseBody() { return clientException.getResponseBodyAsString(); }
   public HttpHeaders getResponseHeaders() { return clientException.getResponseHeaders(); }
   public HttpStatus getResponseStatus() { return clientException.getStatusCode(); }
   // @formatter:on
}
