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

public class PatchRestResourceTest extends RestConnectorTest<PatchRestResource> {
   private PatchRestResource patchResource;
   private String expectedResponseBody;

   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      patchResource = restConnector;
      
      expectedResponseBody = jsonMapper.writeValueAsString( testObjectOne );
      
      // @formatter:off
      this.server.expect( requestTo( RESOURCE_URL ) )
                 .andExpect(  method( HttpMethod.PATCH ))
                 .andRespond( withSuccess( expectedResponseBody, MediaType.APPLICATION_JSON ) );
      // @formatter:on
      
      patchResource.addRequestHeader( "Content-Type", "application/json;charset=UTF-8" );
      patchResource.setRequestBody( expectedResponseBody );
   }

   @Test public void execute_putsRequestObjectAndReturnFullObject(){
      patchResource.execute();
      
      this.server.verify();
      
      assertThat( patchResource.responseStatus(), equalTo( HttpStatus.OK.value() ));
      assertThat( patchResource.responseBody(), equalTo( expectedResponseBody ));
   }
   
   // protected, private helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new PatchRestResource( CONFIGURATION_NAME, RESOURCE_PATH );
   }
}
