package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class PostRestResourceTest extends RestConnectorTest<PostRestResource> {
   private PostRestResource postResource;
   private String expectedResponseBody;

   @Before public void beforeEachTests() throws JsonProcessingException {
      super.beforeEachTests();
      postResource = restConnector;
      
      expectedResponseBody = jsonMapper.writeValueAsString( testObjectOne );
      
      // @formatter:off
      this.server.expect( requestTo( RESOURCE_URL ) )
                 .andExpect(  method( HttpMethod.POST ))
                 .andExpect( header( "Content-Type", "application/json" ))
                 .andRespond( withSuccess( expectedResponseBody, MediaType.APPLICATION_JSON ) );
      // @formatter:on
   }

   @Test public void execute_postsRequestObjectAndStoresResponse(){
      postResource.addRequestHeader( "Content-Type", "application/json;charset=UTF-8" );
      postResource.setRequestBody( expectedResponseBody );
      postResource.execute();
      
      this.server.verify();
      
      assertThat( postResource.responseStatus(), equalTo( HttpStatus.OK.value() ));
      assertThat( postResource.responseBody(), equalTo( expectedResponseBody ));
   }
   
   // protected, private helper methods
   @Override protected void instantiateRestConnector() {
      restConnector = new PostRestResource( CONFIGURATION_NAME, RESOURCE_PATH );
   }
}
