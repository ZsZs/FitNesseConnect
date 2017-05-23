package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;
import com.processpuzzle.fitnesse.connect.configuration.GlueCodeIntegrationTest;

@Category( GlueCodeIntegrationTest.class )
public class DatabaseHasRecordAsFitNesseTest {
   private static final String SQL_QUERY = "SELECT * FROM car ORDER BY id";
   private static final String CONFIGURATION_NAME = "fitnesse-connector";
   private DatabaseHasRecord databaseHasRecord;

   @Before public void beforeEachTest(){
      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester();
      applicationTester.configure( CONFIGURATION_NAME );
      applicationTester.setDataDriverClassName( "org.h2.Driver" );
      applicationTester.setDataUrl( "jdbc:h2:tcp://localhost/~/connector-test" );
      applicationTester.setDataUserName( "SA" );
      applicationTester.setDataPassword( "" );
      
      databaseHasRecord = new DatabaseHasRecord( CONFIGURATION_NAME, SQL_QUERY );
   }
   
   @Test public void query_returnsRecordsAsListOfString() {
      List<List<List<String>>> resultTable = databaseHasRecord.query();
      
      assertThat( resultTable.size(), greaterThanOrEqualTo( 3 ));
   }
   
}
