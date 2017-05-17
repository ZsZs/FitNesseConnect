package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { RestClient.class } )
@org.springframework.boot.test.autoconfigure.web.client.RestClientTest( RestClient.class )
@ActiveProfiles( "unit-test" )
public class GetRestResourceTest extends RestConnectorTest<GetRestResource>{
   private GetRestResource getRestResource;
   
   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      
      getRestResource = restConnector;
      
      this.server.expect( requestTo( RESOURCE_URL ) ).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjectOne ), MediaType.APPLICATION_JSON ) );

      getRestResource.execute();
   }

   @Test public void execute_retrievesResource() throws JsonProcessingException {
      assertThat( Whitebox.getInternalState( getRestResource, "lastResponse" ), notNullValue());
   }

   @Test public void responseStatus_returnsStatusCode() throws JsonProcessingException {
      assertThat( getRestResource.responseStatus(), equalTo( HttpStatus.OK.value() ) );
   }

   @Test public void responseHeader_returnsSpecificHeader() throws JsonProcessingException {
      assertThat( getRestResource.responseHeader( "Content-Type" ), containsString( "[application/json]" ) );
   }

   @Test public void responseHeaders_returnsAllHeadersAsText() throws JsonProcessingException {
      assertThat( getRestResource.responseHeaders(), containsString( "Content-Type=[application/json]" ) );
   }

   @Test public void responseBody_returnsBodyAsText() throws JsonProcessingException {
      assertThat( getRestResource.responseBody(), equalTo( jsonMapper.writeValueAsString( testObjectOne ) ) );
   }

   @Test public void responseTime_measuresResponseTimeInMillis() throws JsonProcessingException {
      assertThat( getRestResource.responseTime(), notNullValue() );
   }

   // protected, private test helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new GetRestResource( "connector", "/api/cars/id" );
   }
}
