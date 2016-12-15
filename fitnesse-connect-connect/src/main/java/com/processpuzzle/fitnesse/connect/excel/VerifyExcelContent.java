package com.processpuzzle.fitnesse.connect.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.common.collect.Lists;

public class VerifyExcelContent extends VerifyExcel {
   protected List<List<String>> tableReturn = Lists.newArrayList();
   protected List<List<String>> tableSource;
   // constructors
   public VerifyExcelContent( final String resourcePath, final String sheetName ) {
      super( resourcePath, sheetName );
   }

   // public accessors and mutators
   public List<List<String>> doTable( List<List<String>> table ) throws IOException {
      this.tableSource = table;
      openWorksheet();
      compareTable();
      return tableReturn;
   }

   // protected, private helper methods
   private void compareTable() {
      int rowIndex = 0;
      for( List<String> expectedRow : tableSource ){
         List<String> valueRowReturn = compareRow( expectedRow, rowIndex );
         tableReturn.add( valueRowReturn );
         rowIndex++;
      }
   }

   private List<String> compareRow( List<String> expectedRow, int rowIndex ) {
      final List<String> valueRowReturn = Lists.newArrayList();
      int columnIndex = 0;
      for( String expectedCell : expectedRow ){
         String returnFlag = verifyCell( expectedCell, rowIndex, columnIndex );
         valueRowReturn.add( returnFlag );
         columnIndex++;
      }
      return valueRowReturn;
   }

   private String verifyCell( String expectedCell, int rowIndex, int columnIndex ) {
      Row currentRow = excelSheet.getRow( rowIndex );
      Cell currentCell = currentRow.getCell( columnIndex );
      String currentCellValue = currentCell.getStringCellValue();
      if( expectedCell.equals( currentCellValue )) return "pass";
      else return "fail:" + currentCellValue;
   }
}
