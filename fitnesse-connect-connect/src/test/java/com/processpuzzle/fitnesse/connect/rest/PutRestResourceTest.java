package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class PutRestResourceTest extends RestConnectorTest<PutRestResource> {
   private PutRestResource putResource;
   private String expectedResponseBody;

   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      putResource = restConnector;
      
      expectedResponseBody = jsonMapper.writeValueAsString( testObjectOne );
      
      // @formatter:off
      this.server.expect( requestTo( RESOURCE_URL ) )
                 .andExpect(  method( HttpMethod.PUT ))
                 .andRespond( withSuccess( expectedResponseBody, MediaType.APPLICATION_JSON ) );
      // @formatter:on
      
      putResource.addRequestHeader( "Content-Type", "application/json;charset=UTF-8" );
      putResource.setRequestBody( expectedResponseBody );
   }

   @Test public void execute_putsRequestObjectAndReturnFullObject(){
      putResource.execute();
      
      this.server.verify();
      
      assertThat( putResource.responseStatus(), equalTo( HttpStatus.OK.value() ));
      assertThat( putResource.responseBody(), equalTo( expectedResponseBody ));
   }
   
   // protected, private helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new PutRestResource( CONFIGURATION_NAME, RESOURCE_PATH );
   }
}
