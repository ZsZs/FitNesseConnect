package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class RestScenarioTest extends RestConnectorTest<RestScenario> {
   private RestScenario restScenario;

   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      this.restScenario = restConnector;      
   }

   @Test public void getResource_whenCalledWithoutUri_usesBasePath() throws JsonProcessingException {
      this.server.expect( requestTo( RESOURCE_URL ) ).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjects ), MediaType.APPLICATION_JSON ) );

      restScenario.getResource( null );
      
      assertThat( Whitebox.getInternalState( restScenario, "lastResponse" ), notNullValue());
      assertThat( restScenario.responseBody(), equalTo( jsonMapper.writeValueAsString( testObjects ) ));
   }
   
   @Test public void getResource_whenCalledWittUri_patchesUri() throws JsonProcessingException {
      this.server.expect( requestTo( RESOURCE_URL + "/1"  )).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjectOne ), MediaType.APPLICATION_JSON ) );

      restScenario.getResource( "/1" );
      
      assertThat( restScenario.responseBody(), equalTo( jsonMapper.writeValueAsString( testObjectOne ) ));
   }

   // protected, private test helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new RestScenario( "connector", "/api/cars" );
   }
}
