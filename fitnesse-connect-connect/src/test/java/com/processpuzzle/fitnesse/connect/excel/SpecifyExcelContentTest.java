package com.processpuzzle.fitnesse.connect.excel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class SpecifyExcelContentTest {
   private static final String EXCEL_FILE = "file:./src/test/fitnesse/files/sample/SampleTwo.xlsx:Sheet_1";
   private SpecifyExcelContent specifyExcel;
   
   @Before public void beforeEachTest() throws FileNotFoundException, IOException{
      specifyExcel = new SpecifyExcelContent( EXCEL_FILE );
   }
   
   @Test public void verifyColumn_whenHeaderColumnExist_returnsPass(){
      specifyExcel.setColumn( "String Column" );
      specifyExcel.setDataType( "String" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
   }
   
   @Test public void verifyColumn_whenHeaderColumnNotExistAndIsMandatory_returnsFailed(){
      specifyExcel.setColumn( "No Column" );
      specifyExcel.setIsMandatory( "yes" );
      specifyExcel.setDataType( "String" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "failed: column not defined" ));
   }
   
   @Test public void verifyColumn_whenDataTypeMatches_returnsPass(){
      specifyExcel.setColumn( "String Column" );
      specifyExcel.setDataType( "String" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
      
      specifyExcel.setColumn( "Integer Column" );
      specifyExcel.setDataType( "Integer" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
      
      specifyExcel.setColumn( "Date Column" );
      specifyExcel.setDataType( "Date" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
   }
   
   @Test public void verifyColumn_whenColumnIsMandatory_returnsPass(){
      specifyExcel.setColumn( "Optional Column" );
      specifyExcel.setDataType( "String" );
      specifyExcel.setIsMandatory( "no" );
      specifyExcel.setIsValueMandatory( "yes" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
   }
   
   @Test public void verifyColumn_whenColumnValueIsMandatory_returnsPass(){
      specifyExcel.setColumn( "Optional Value Column" );
      specifyExcel.setDataType( "String" );
      specifyExcel.setIsMandatory( "yes" );
      specifyExcel.setIsValueMandatory( "no" );
      assertThat( specifyExcel.verifyColumn(), equalTo( "pass" ));
   }
}
