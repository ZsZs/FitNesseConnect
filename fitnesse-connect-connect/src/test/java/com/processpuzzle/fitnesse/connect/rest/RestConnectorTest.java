package com.processpuzzle.fitnesse.connect.rest;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ConnectorApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { RestClient.class } )
@org.springframework.boot.test.autoconfigure.web.client.RestClientTest( RestClient.class )
@ActiveProfiles( "unit-test" )
@EnableConfigurationProperties( ConnectorApplicationConfiguration.class )
public abstract class RestConnectorTest<C extends RestConnector > {
   protected static final String RESOURCE_PATH = "/api/cars";
   protected static final String RESOURCE_URL = "http://127.0.0.1:8080/dev/api/cars";
   protected static final String CONFIGURATION_NAME = "connector";
   @Autowired protected ObjectMapper jsonMapper;
   @Autowired ApplicationContext applicationContext;
   @Autowired ConnectorApplicationConfiguration connectorConfiguration;
   protected C restConnector;
   @Autowired protected MockRestServiceServer server;
   protected TestObject testObjectOne;
   protected TestObject testObjectTwo;
   protected List<TestObject> testObjects = Lists.newArrayList();

   public void beforeEachTests() throws JsonProcessingException {
      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester( applicationContext );
      applicationTester.addConfiguration( "connector", connectorConfiguration );
      
      testObjectOne = new TestObject( "Some text", 1 );
      testObjects.add( testObjectOne );
      
      testObjectTwo = new TestObject( "Other text", 2 );
      testObjects.add( testObjectTwo );

      instantiateRestConnector();
      
      server = MockRestServiceServer.bindTo( restConnector.getRestClient().getRestTemplate() ).build();
      
   }
   
   // protected, private helper methods
   protected abstract void instantiateRestConnector();
}
