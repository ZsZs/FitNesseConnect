package com.processpuzzle.fitnesse.connect.rest;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public abstract class RestConnectorTest<C extends RestConnector > {
   protected static final String RESOURCE_URL = "http://127.0.0.1:8080/dev/api/cars/id";
   @Autowired protected ObjectMapper jsonMapper;
   protected C restConnector;
   @Autowired protected MockRestServiceServer server;
   protected TestObject testObjectOne;

   @Before public void beforeEachTests() throws JsonProcessingException {
      testObjectOne = new TestObject( "Some text", 1 );

      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester();
      applicationTester.initialize( "unit-test" );

      instantiateRestConnector();
      
      server = MockRestServiceServer.bindTo( restConnector.getRestClient().getRestTemplate() ).build();
   }
   
   // protected, private helper methods
   protected abstract void instantiateRestConnector();
}
