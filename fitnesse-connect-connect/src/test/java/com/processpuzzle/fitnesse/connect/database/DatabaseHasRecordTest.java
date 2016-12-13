package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
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
