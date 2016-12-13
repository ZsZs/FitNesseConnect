package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class } )
@ActiveProfiles( "unit-test" )
public class DeleteRecordFromTestDatabaseTest {
   private DeleteRecordFromDatabase deleteRecord;
   private DatabaseHasRecord databaseHasRecord;

   @Before public void beforeEachTest() {
      createRecords();
      
      deleteRecord = new DeleteRecordFromDatabase( "connector", "delete from account where account_name like 'name%';" );
      databaseHasRecord = new DatabaseHasRecord( "connector", "select * from account where account_name like 'name%';" );
   }

   @Test public void query_returnsPassedInserts() {
      assumeThat( databaseHasRecord.query().size(), equalTo( 2 ));
      
      assertThat( deleteRecord.delete(), is( true ) );
      assertThat( databaseHasRecord.query().size(), equalTo( 0 ));
   }

   // protected, private test helper methods
   @SuppressWarnings( "unchecked" )
   private void createRecords() {
      List<List<String>> table;
      InsertRecordIntoDatabase insertRecord = new InsertRecordIntoDatabase( "connector", "account" );

      List<String> headerRow = Lists.newArrayList( "account_name", "password" );
      List<String> insert1 = Lists.newArrayList( "'name1'", "'pass1'" );
      List<String> insert2 = Lists.newArrayList( "'name2'", "'pass2'" );
      table = Lists.newArrayList( headerRow, insert1, insert2 );
      insertRecord.doTable( table );
   }   
}
