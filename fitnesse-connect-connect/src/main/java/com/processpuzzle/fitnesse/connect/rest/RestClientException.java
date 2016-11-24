package com.processpuzzle.fitnesse.connect.rest;

import java.text.MessageFormat;

public class RestClientException extends Exception {
   private static final long serialVersionUID = 5880058073517527976L;
   private static final String messageTemplate = "Invoking {0} location with {1} method by {2} resource failed.";

   public RestClientException( String resourceURI, String httpMethod, String resource ){
      super( MessageFormat.format( messageTemplate, new Object[] { resourceURI, httpMethod, resource }));
   }
   
   public RestClientException( String resourceURI, String httpMethod, String resource, Throwable cause ){
      super( MessageFormat.format( messageTemplate, new Object[] { resourceURI, httpMethod, resource }), cause );
   }
}
