package com.processpuzzle.fitnesse.connect.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelCellVerifier{
   private final Cell cell;

   // constructors
   public ExcelCellVerifier( Cell cell ) {
      this.cell = cell;
   }

   public boolean isTypeOf( ColumnDataTypes columnType ) {
      boolean isTypeOf = false;
      String cellValue;
      @SuppressWarnings( "deprecation" ) CellType cellType = cell.getCellTypeEnum();
      switch( columnType ) {
         case STRING:
            cellValue = cell.getStringCellValue();
            isTypeOf = cellType.equals( CellType.STRING );
            break;
         case INTEGER:
            cellValue = new Integer( (int) cell.getNumericCellValue() ).toString();
            isTypeOf = cellType.equals( CellType.NUMERIC ) && cellValue.matches( "^-?\\d+$" );
            break;
         case DATE:
            cellValue = cell.getStringCellValue();
            isTypeOf = cellType.equals( CellType.STRING ) && isValidDateFormat( "yyyy.MM.dd", cellValue );
            break;
         default:
            isTypeOf = false;
      }
      return isTypeOf;
   }

   public boolean isValidDateFormat( String format, String value ) {
      Date date = null;
      try{
         SimpleDateFormat sdf = new SimpleDateFormat( format );
         date = sdf.parse( value );
         if( !value.equals( sdf.format( date ) ) ){
            date = null;
         }
      }catch( ParseException ex ){
         // sip the exception
      }
      return date != null;
   }

   @SuppressWarnings( "deprecation" )
   public boolean hasValue() {
      if( cell.getCellTypeEnum() != CellType.BLANK ) return true;
      else return false;
   }
}
