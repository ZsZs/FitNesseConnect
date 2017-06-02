package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.ConnectorApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
@SpringBootTest( classes={ ConnectorApplicationConfiguration.class })
@EnableConfigurationProperties
@ActiveProfiles( "unit-test" )
public class DatabaseFixtureTest {
   private static final String CONFIGURATION_NAME = "connector";
   @Autowired ApplicationContext applicationContext;
   @Autowired ConnectorApplicationConfiguration connectorConfiguration;

   @Before public void beforeEachTest(){
      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester( applicationContext );
      applicationTester.addConfiguration( CONFIGURATION_NAME, connectorConfiguration );
   }
   
   @Test public void instantiation_createsDatabaseClient(){      
      TestDatabaseFixture testFixture = new TestDatabaseFixture( CONFIGURATION_NAME, "select * from account" );
      
      assertThat( testFixture.databaseClient, notNullValue() );
   }

   @Test( expected = UnknownApplicationConfigurationException.class ) public void instantiation_whenConfigurationNotExist_throwsException(){
      new TestDatabaseFixture( "Unknown configuration", "select * from account" );
   }
}
