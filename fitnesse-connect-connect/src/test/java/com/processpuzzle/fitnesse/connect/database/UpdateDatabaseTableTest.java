package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

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
public class UpdateDatabaseTableTest {
   private DeleteRecordFromDatabase deleteRecord;
   private InsertRecordIntoDatabase insertRecord;
   private List<List<String>> table;
   private UpdateDatabaseTable updateTable;
   private Integer testRecordId;

   @Before public void beforeEachTest(){
      insertRecord = new InsertRecordIntoDatabase( "connector", "account" );
      List<String> headerRow = Lists.newArrayList( "Generated:id", "account_name", "password" );
      List<String> insert1 = Lists.newArrayList( "$AccountId1=", "'name1'", "'pass1'" );
      table = Lists.newArrayList( headerRow, insert1 );
      insertRecord.doTable( table );

      DatabaseHasRecord databaseHasRecord = new DatabaseHasRecord( "connector", "select id from account order by id desc" );
      List<List<List<String>>> actualRecords = databaseHasRecord.query();
      testRecordId = Integer.parseInt( actualRecords.get( 0 ).get( 0 ).get( 1 ));
      
      updateTable = new UpdateDatabaseTable( "connector", "account", "id = '" + testRecordId.toString() + "';" );
      
      headerRow = Lists.newArrayList( "account_name", "password" );
      List<String> update1 = Lists.newArrayList( "'newName'", "'newPassord'" );
      table = Lists.newArrayList( headerRow, update1 );

   }
   
   @After public void afterEachTest() {
      deleteRecord = new DeleteRecordFromDatabase( "connector", "delete from account where ID > " + Integer.toString( testRecordId ));
      deleteRecord.delete();
   }

   @Test public void doTable_returnsPassedUpdates() {
      List<List<String>> returnTable = updateTable.doTable( table );

      assertThat( returnTable.size(), equalTo( 2 ) );
      assertThat( returnTable.get( 1 ).get( 0 ), containsString( "pass" ) );
   }
   
}
