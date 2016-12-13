package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;
import com.processpuzzle.fitnesse.connect.configuration.GlueCodeIntegrationTest;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class } )
@ActiveProfiles( "local" )
@Category( GlueCodeIntegrationTest.class )
public class HttpResourceConnectorTest {
   
   @Test public void dummyTest(){
      assertThat( true, is( true ));
   }
}
