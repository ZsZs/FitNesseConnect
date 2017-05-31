package com.processpuzzle.fitnesse.connect.testbed.service;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

public class CarNotFoundException extends ServiceException {
   private static final long serialVersionUID = 9020500737933980744L;
   private static final String messageTemplate = "Car with id {0} not found.";
   
   public CarNotFoundException( Long carId ){
      super( MessageFormat.format( messageTemplate, new Object[] { carId }), HttpStatus.NOT_FOUND, ServiceErrors.CAR_NOT_FOUND );
   }
}
