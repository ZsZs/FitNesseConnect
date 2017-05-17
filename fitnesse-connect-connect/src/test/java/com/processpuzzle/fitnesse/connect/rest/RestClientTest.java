package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { RestClient.class } )
@org.springframework.boot.test.autoconfigure.web.client.RestClientTest
public class RestClientTest {
   private static final String RESOURCE_ID = "1";
   private static final String RESOURCE_URI = "http://localhost/testobjects";
   @Autowired private ObjectMapper jsonMapper;
   @Autowired private RestClient restClient;
   @Autowired private RestTemplate restTemplate;
   @Autowired private MockRestServiceServer server;
   private TestObject testObjectOne;
   private TestObject testObjectTwo;

   @Before public void beforeEachTests(){
      testObjectOne = new TestObject( "some text", 1 );
      testObjectTwo = new TestObject( "other text", 2 );
      
      server = MockRestServiceServer.createServer( restTemplate );      
   }
   
   @Test
   public void getResource_unmarshallsSingleObject() throws JsonProcessingException {
      this.server.expect( requestTo( RESOURCE_URI + "/1" )).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjectOne ), MediaType.APPLICATION_JSON ) );
      
      ResponseEntity<TestObject> response = restClient.getResource( RESOURCE_ID, TestObject.class, "" );
      TestObject responseObject = response.getBody();
      
      assertThat( responseObject, samePropertyValuesAs( testObjectOne ));
   }
   
   private class TestObject {
      private String textProperty;
      private Integer intProperty;
      
      TestObject( String textProperty, Integer intProperty ){
         this.textProperty = textProperty;
         this.intProperty = intProperty;
      }

      // @formatter:off
      @SuppressWarnings( "unused" ) public String getTextProperty() { return textProperty; }
      @SuppressWarnings( "unused" ) public Integer getIntProperty() { return intProperty; }      
      // @formatter:on
   }
}
