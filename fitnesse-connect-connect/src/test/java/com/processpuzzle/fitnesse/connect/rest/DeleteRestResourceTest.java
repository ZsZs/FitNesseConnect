package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DeleteRestResourceTest extends RestConnectorTest<DeleteRestResource>{
   private DeleteRestResource deleteRestResource;
   
   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      
      deleteRestResource = restConnector;
      
      this.server.expect( requestTo( RESOURCE_URL + "/1" )).andRespond( withSuccess( "Resource deleted.", MediaType.APPLICATION_JSON ) );
      
      deleteRestResource.execute();
   }
   
   @Test public void execute_sendsDeleteMessage() throws JsonProcessingException {
      assertThat( deleteRestResource.responseStatus(), equalTo( HttpStatus.OK.value() ) );
   }
   
   // protected, private test helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new DeleteRestResource( "connector", "/api/cars/1" );
   }
}
