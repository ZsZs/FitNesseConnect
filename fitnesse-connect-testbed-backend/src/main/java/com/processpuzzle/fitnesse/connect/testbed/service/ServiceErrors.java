package com.processpuzzle.fitnesse.connect.testbed.service;

import org.springframework.http.HttpStatus;

public enum ServiceErrors {
   CAR_NOT_FOUND( 1, "Car not found.", HttpStatus.NOT_FOUND ),
   INVALID_RESOURCE_PATH( 2, "Invalid resource path.", HttpStatus.BAD_REQUEST ),
   INVALID_OBJECT_PROPERTIES( 3, "Invalid object properties.", HttpStatus.NOT_ACCEPTABLE ),
   INVALID_QUERY_PARAMETER( 4, "Invalid query parameter.", HttpStatus.BAD_REQUEST ),
   INVALID_DOWNLOAD_TOKEN ( 5, "Invalid download token.", HttpStatus.BAD_REQUEST ),
   EXPIRED_DOWNLOAD_TOKEN( 6, "Expired download token.", HttpStatus.GONE );

   // constructors
   ServiceErrors( int errorNumber, String errorMessage, HttpStatus status ){
      this.number = errorNumber;
      this.message = errorMessage;
      this.status = status;
   }
   
   private final int number;
   private final String message;
   private final HttpStatus status;
   
   // properties
   // @formatter:off
   public int getErrorNumber(){ return number; }
   public String getErrorMessage() { return message; }
   public HttpStatus getStatus() { return status; }
   // @formatter:on
}
