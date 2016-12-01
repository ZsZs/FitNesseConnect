package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ConnectorApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class, ConnectorApplicationConfiguration.class } )
@EnableAutoConfiguration
@ActiveProfiles( "unit-test" )
public class InsertRecordIntoTestDatabaseTest {
   private DatabaseHasRecord databaseHasRecord;
   private DeleteRecordFromDatabase deleteRecord;
   private Integer highestId;
   private InsertRecordIntoDatabase insertRecord;
   private int originalNumberOfRecords;
   private List<List<String>> table;
   private List<List<List<String>>> actualRecords;

   @SuppressWarnings( "unchecked" ) @Before public void beforeEachTest() {
      insertRecord = new InsertRecordIntoDatabase( "connector", "account" );

      List<String> headerRow = Lists.newArrayList( "Generated:id", "account_name", "password" );
      List<String> insert1 = Lists.newArrayList( "$AccountId1=", "'name1'", "'pass1'" );
      List<String> insert2 = Lists.newArrayList( "$AccountId2=", "'name2'", "'pass2'" );
      table = Lists.newArrayList( headerRow, insert1, insert2 );

      databaseHasRecord = new DatabaseHasRecord( "connector", "select id from account order by id desc" );
      actualRecords = databaseHasRecord.query();
      originalNumberOfRecords = actualRecords.size();
      highestId = Integer.parseInt( actualRecords.get( 0 ).get( 0 ).get( 1 ));
   }

   @After public void afterEachTest() {
      deleteRecord = new DeleteRecordFromDatabase( "connector", "delete from account where ID > " + Integer.toString( highestId ));
      deleteRecord.delete();
   }

   @Test public void doTable_returnsPassedInserts() {
      List<List<String>> returnTable = insertRecord.doTable( table );

      actualRecords = databaseHasRecord.query();
      
      assertThat( actualRecords.size(), equalTo( originalNumberOfRecords + 2 ) );
      
      assertThat( returnTable.size(), equalTo( 3 ) );
      assertThat( returnTable.get( 1 ).get( 0 ), containsString( "pass:" ) );
      assertThat( StringUtils.substringAfter( returnTable.get( 1 ).get( 0 ), "pass:" ), greaterThanOrEqualTo( Integer.toString( highestId + 1 )));
      assertThat( returnTable.get( 2 ).get( 0 ), containsString( "pass:" ) );
      assertThat( StringUtils.substringAfter( returnTable.get( 2 ).get( 0 ), "pass:" ), greaterThanOrEqualTo( Integer.toString( highestId + 2 )));
   }
}
