package com.processpuzzle.fitnesse.connect.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;
import com.processpuzzle.fitnesse.connect.configuration.GlueCodeIntegrationTest;

@Category( GlueCodeIntegrationTest.class )
@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class } )
@ActiveProfiles( "local" )
public class GetRestResourceBasicAuthenticationTest {
   protected static final String CONFIGURATION_NAME = "connector";
   protected static final String RESOURCE_PATH = "/api/secure";
   private static final String PASSORD = "password";
   private static final String USER_NAME = "user";
   
   @Test public void getRestResorce_whenResourceExist_retrievesObject(){
      GetRestResource getResource = new GetRestResource( CONFIGURATION_NAME, RESOURCE_PATH );
      getResource.basicAuthentication( USER_NAME, PASSORD );
      getResource.execute();
      assertThat( getResource.responseStatus(), equalTo( 200 ));
   }   

}
