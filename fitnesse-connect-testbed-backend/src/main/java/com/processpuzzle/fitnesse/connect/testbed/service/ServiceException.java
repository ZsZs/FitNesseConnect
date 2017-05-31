package com.processpuzzle.fitnesse.connect.testbed.service;

import org.springframework.http.HttpStatus;

public abstract class ServiceException extends Exception {
   private static final long serialVersionUID = -6397059176880480244L;
   protected final HttpStatus responseStatus;
   protected final ServiceErrors serviceError;
   
   protected ServiceException( String message, HttpStatus responseStatus, ServiceErrors serviceError ){
      super( message );
      this.responseStatus = responseStatus;
      this.serviceError = serviceError;
   }
   
   // properties
   // @formatter:off
   public HttpStatus getResponseStatus() { return responseStatus; }
   public ServiceErrors getServiceError() { return serviceError; }
   // @formatter:on
}
