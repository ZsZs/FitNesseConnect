package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class } )
@ActiveProfiles( "unit-test" )
public class ResourceConnectorTest {
   private static final String NEW_RESOURCE_NAME = "SampleNew.txt";
   private static final String RELATIVE_DIRECTORY_PATH = "file:./src/test/fitnesse/files/sample/";

   @Ignore @Test public void createResource_whenPathAndNameIsValid_createsNewResource(){
      ResourceConnector resourceConnector = new ResourceConnector( RELATIVE_DIRECTORY_PATH + NEW_RESOURCE_NAME );
      
      Resource newResource = resourceConnector.createResource();
      
      assertThat( newResource, notNullValue() );
      assertThat( newResource.getFilename(), equalTo( NEW_RESOURCE_NAME ));
   }
   
   @Test public void retrieveResource_whenRelativeFilePathIsGiven_returnsOneResource() throws IOException{
      ResourceConnector resourceConnector = new ResourceConnector( RELATIVE_DIRECTORY_PATH + "SampleOne.xlsx" );
      
      Resource foundResource = resourceConnector.retrieveResource();
      
      assertThat( foundResource, notNullValue() );
      assertThat( foundResource.getFilename(), equalTo( "SampleOne.xlsx" ));
      assertThat( foundResource.contentLength(), equalTo( 8783L ));
   }

   @Test public void retrieveResources_whenRelativeDirectoryPathIsGiven_returnsManyResources() throws IOException{
      ResourceConnector resourceConnector = new ResourceConnector( RELATIVE_DIRECTORY_PATH + "*" );
      
      List<Resource> foundResources = resourceConnector.retrieveResources();
      
      assertThat( foundResources.size(), equalTo( 2 ) );
   }
}
