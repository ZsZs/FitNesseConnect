package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GetRestResourceLayerTest extends RestConnectorTest<GetRestResource>{
   private GetRestResource getResource;
   
   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      
      getResource = restConnector;
      
      this.server.expect( requestTo( RESOURCE_URL ) ).andRespond( withSuccess( jsonMapper.writeValueAsString( testObjects ), MediaType.APPLICATION_JSON ) );
      
      getResource.execute();
   }

   @Test public void execute_retrievesResource() throws JsonProcessingException {
      assertThat( Whitebox.getInternalState( getResource, "lastResponse" ), notNullValue());
   }

   @Test public void responseStatus_returnsStatusCode() throws JsonProcessingException {
      assertThat( getResource.responseStatus(), equalTo( HttpStatus.OK.value() ) );
   }

   @Test public void responseHeader_returnsSpecificHeader() throws JsonProcessingException {
      assertThat( getResource.responseHeader( "Content-Type" ), containsString( "[application/json]" ) );
   }

   @Test public void responseHeaders_returnsAllHeadersAsText() throws JsonProcessingException {
      assertThat( getResource.responseHeaders(), containsString( "Content-Type=[application/json]" ) );
   }

   @Test public void responseBody_returnsBodyAsText() throws JsonProcessingException {
      assertThat( getResource.responseBody(), equalTo( jsonMapper.writeValueAsString( testObjects ) ) );
   }

   @Test public void responseBodyProperty_selectsPropertyFromResponse() {
      assertThat( getResource.responseBodyProperty( "$.[0].['textValue']" ).toString(), containsString( testObjectOne.getTextValue() ));
      assertThat( getResource.responseBodyProperty( "$.[0].['numberValue']" ), equalTo( testObjectOne.getNumberValue() ));
   }

   @Test public void responseTime_measuresResponseTimeInMillis() throws JsonProcessingException {
      assertThat( getResource.responseTime(), notNullValue() );
   }
   
   @Test public void addRequestHeader_buildsHeadersToSend(){
      getResource.addRequestHeader( "test_header_key", "test_header_value" );
      assertThat( getResource.requestHeaders.containsKey( "test_header_key" ), is( true ) );
      assertThat( getResource.requestHeaders.get( "test_header_key" ), equalTo( Lists.newArrayList( "test_header_value" )));
   }

   // protected, private test helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new GetRestResource( CONFIGURATION_NAME, RESOURCE_PATH );
   }
}
