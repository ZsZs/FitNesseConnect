package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { RestClient.class })
@org.springframework.boot.test.autoconfigure.web.client.RestClientTest( RestClient.class )
public class RestClientTest {
   private static final String RESOURCE_ID = "1";
   private static final String RESOURCE_URI = "http://document.nomoretools.com/documents";
   @Autowired private MockRestServiceServer server;
   @Autowired private ObjectMapper jsonMapper;
   private RestClient restClient;
   private TestObject testObjectOne;

   @Before public void beforeEachTests() {
      testObjectOne = new TestObject( "Some text", 1 );
      restClient = RestClient.create();
      server = MockRestServiceServer.bindTo( restClient.getRestTemplate() ).build();
   }

   @Test public void getResource_unmarshallsSingleObject() throws JsonProcessingException, RestClientException {
      this.server.expect( requestTo( RESOURCE_URI + "/" + RESOURCE_ID ) ).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjectOne ), MediaType.APPLICATION_JSON ) );
      
      ResponseEntity<TestObject> response = restClient.getResource( RESOURCE_URI + "/" + RESOURCE_ID, TestObject.class, "" );
      TestObject responseObject = response.getBody();
      
      assertThat( response.getStatusCode(), equalTo( HttpStatus.OK ));
      assertThat( responseObject, samePropertyValuesAs( testObjectOne ));
   }
}
