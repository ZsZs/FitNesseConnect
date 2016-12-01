package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;
import com.processpuzzle.fitnesse.connect.database.DatabaseHasRecord;

@RunWith( SpringRunner.class )
@EnableAutoConfiguration
@ComponentScan( basePackages = { "com.processpuzzle.fitnesse.connect.database" })
@ActiveProfiles( "unit-test" )
public class DatabaseHasRecordTest {
   private DatabaseHasRecord databaseHasRecord;

   @Before public void beforeEachTest(){
      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester();
      applicationTester.initialize( "unit-test" );
      
      databaseHasRecord = new DatabaseHasRecord( "connector", "select * from account" );
   }
   
   @Test public void query_returnsRecordsAsListOfString() {
      assertThat( databaseHasRecord.query().size(), equalTo( 4 ));
   }
   
}
