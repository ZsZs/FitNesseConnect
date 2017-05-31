package com.processpuzzle.fitnesse.connect.testbed.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ServiceErrorResponse {
   private final String id;
   private final String message;
   @JsonSerialize( using = CustomLocalDateTimeSerializer.class ) @JsonDeserialize( using = CustomLocalDateTimeDeserializer.class ) private final LocalDateTime timeStamp;

   public ServiceErrorResponse( String id, String message ){
      this.id = id;
      this.message = message;
      this.timeStamp = LocalDateTime.now();      
   }
   
   // properties
   // @formatter:off
   public String getId() { return id; }
   public String getMessage() { return message; }
   public LocalDateTime getTimeStamp() { return timeStamp; }
   // @formatter:on
   
}
