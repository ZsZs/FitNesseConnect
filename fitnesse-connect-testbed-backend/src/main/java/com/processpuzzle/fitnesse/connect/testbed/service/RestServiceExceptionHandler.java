package com.processpuzzle.fitnesse.connect.testbed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class RestServiceExceptionHandler extends ResponseEntityExceptionHandler {
   @Autowired ObjectMapper jsonMapper;
   
   @ExceptionHandler( value = { CarNotFoundException.class } ) protected ResponseEntity<Object> handleServiceException( ServiceException ex, WebRequest request ) throws JsonProcessingException {
      ServiceErrorResponse errorResponse = new ServiceErrorResponse( ex.getServiceError().name(), ex.getMessage() );
      
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );
      String bodyOfResponse = jsonMapper.writeValueAsString( errorResponse );
      
      return handleExceptionInternal( ex, bodyOfResponse, headers, ex.getResponseStatus(), request );
   }
}
