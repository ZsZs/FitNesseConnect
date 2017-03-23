package com.processpuzzle.fitnesse.connect.excel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class VerifyExcelContentTest {
   private static final String EXCEL_FILE = "file:./src/test/fitnesse/files/sample/SampleOne.xlsx";
   private static final String SHEET_NAME = "Sheet_1";
   private List<List<String>> expectedTable;
   private VerifyExcelContent verifyExcelContent;
   
   @Before public void beforeEachTest(){
      verifyExcelContent = new VerifyExcelContent( EXCEL_FILE, SHEET_NAME );
   }

   @Test public void doTable_whenTablesMatch_returnsPass() throws IOException {
      produceProperExpectedTable();
      List<List<String>> returnTable = verifyExcelContent.doTable( expectedTable );
      
      assertThat( returnTable.size(), equalTo( 3 ) );
      assertThat( returnTable.get( 0 ), hasItems( "pass", "pass" ) );
      assertThat( returnTable.get( 1 ), hasItems( "pass", "pass" ) );
      assertThat( returnTable.get( 2 ), hasItems( "pass", "pass" ) );
   }
   
   @Test public void doTable_whenTablesDiffer_returnsFail() throws IOException {
      produceDifferingExpectedTable();
      List<List<String>> returnTable = verifyExcelContent.doTable( expectedTable );
      
      assertThat( returnTable.size(), equalTo( 3 ) );
      assertThat( returnTable.get( 0 ), hasItems( "pass", "pass" ) );
      assertThat( returnTable.get( 1 ), hasItems( "pass", "fail:2_B" ) );
      assertThat( returnTable.get( 2 ), hasItems( "pass", "pass" ) );
   }
   
   // protected, private test helper methods
   private void produceDifferingExpectedTable(){
      produceProperExpectedTable();
      expectedTable.get( 1 ).set( 1, "something" );
   }
   
   private void produceProperExpectedTable() {
      List<String> row1 = Lists.newArrayList( "Column A", "Column B" );
      List<String> row2 = Lists.newArrayList( "2_A", "2_B" );
      List<String> row3 = Lists.newArrayList( "3_A", "3_B" );
      expectedTable = Lists.newArrayList( row1, row2, row3 );
   }
}
