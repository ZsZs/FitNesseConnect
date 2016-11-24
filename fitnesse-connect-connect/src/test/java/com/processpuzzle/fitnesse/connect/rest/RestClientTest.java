package com.processpuzzle.fitnesse.connect.rest;

import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.processpuzzle.fitnesse.connect.rest.RestClient;

public class RestClientTest {
   private static final String RESOURCE_ID = "1";
   private static final String RESOURCE_URI = "http://document.nomoretools.com/documents";

   @Ignore @Test
   public void getResource_unmarshallsSingleObject() {

      RestClient restClient = RestClient.create( RESOURCE_URI );
      ResponseEntity<Document> response = restClient.getResource( RESOURCE_ID, Document.class, "" );
      Document document = response.getBody();
      
      assertThat( document.getName(), org.hamcrest.CoreMatchers.equalTo( "Analysis & Design" ) );
   }
   
}
