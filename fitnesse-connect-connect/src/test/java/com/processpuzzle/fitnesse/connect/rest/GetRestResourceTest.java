package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;
import com.processpuzzle.fitnesse.connect.configuration.GlueCodeIntegrationTest;

@Category( GlueCodeIntegrationTest.class )
@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class } )
@ActiveProfiles( "local" )
public class GetRestResourceTest {
   protected static final String RESOURCE_PATH = "/api/cars";
   protected static final String CONFIGURATION_NAME = "connector";
   @Autowired private IntegratedApplicationTester appTester;
   
   @Before public void beforeEachTest(){
   }
   
   @Test public void contextLoads(){
      assertThat( appTester, notNullValue() );
   }
   
   @Test public void getRestResorce_whenResourceExist_retrievesObject(){
      GetRestResource getResource = new GetRestResource( CONFIGURATION_NAME, RESOURCE_PATH + "/0" );
      getResource.execute();
      assertThat( getResource.responseStatus(), equalTo( 200 ));
   }   
   
   @Test public void getRestResorce_whenResourceNotExist_returnsErrorObject(){
      GetRestResource getResource = new GetRestResource( CONFIGURATION_NAME, RESOURCE_PATH + "/999" );
      getResource.execute();
      assertThat( getResource.responseStatus(), equalTo( 404 ));
   }   
}
