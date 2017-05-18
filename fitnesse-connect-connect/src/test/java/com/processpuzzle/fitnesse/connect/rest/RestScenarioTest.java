package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { RestClient.class } )
@org.springframework.boot.test.autoconfigure.web.client.RestClientTest( RestClient.class )
@ActiveProfiles( "unit-test" )
public class RestScenarioTest extends RestConnectorTest<RestScenario> {
   private RestScenario restScenario;

   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      this.restScenario = restConnector;
      
      this.server.expect( requestTo( RESOURCE_URL ) ).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjectOne ), MediaType.APPLICATION_JSON ) );
   }

   @Test public void getResource_retrievesResource() throws JsonProcessingException {
      restScenario.getResource( null );
      
      assertThat( Whitebox.getInternalState( restScenario, "lastResponse" ), notNullValue());
   }

   // protected, private test helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new RestScenario( "connector", "/api/cars/id" );
   }
}