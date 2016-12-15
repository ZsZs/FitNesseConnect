package com.processpuzzle.fitnesse.connect.excel;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SpecifyExcelContent extends VerifyExcel {
   private static final String PASS_TOKEN = "pass";
   @SuppressWarnings( "unused" ) private String columnDescription;
   private Integer columnIndex;
   private String columnName;
   private ColumnDataTypes columnType;
   private Boolean columnMandatory = false;
   private Boolean columnValueMandatory = false;
   private String returnMessage;

   // constructors
   public SpecifyExcelContent( final String resourcePath ) throws FileNotFoundException, IOException {
      super( determineFilePart( resourcePath ), determineSheetName( resourcePath ) );
      openWorksheet();
   }

   // public accessors and mutators
   public String verifyColumn() {
      determineCurrentColumn();
      verifyColumnName();

      if( this.columnIndex != null ){
         if( this.columnType != null )
            verifyColumnType();
         if( this.columnMandatory != null )
            verifyMandatoryColumn();
         if( this.columnValueMandatory != null )
            verifyMandatoryColumnValue();
      }

      return returnMessage;
   }

   // properties
   // @formatter:off
   public void setColumn( String columnName ){ this.columnName = columnName; }
   public void setDataType( String columnType ){ this.columnType = ColumnDataTypes.parse( columnType ); }
   public void setDescription( String columnDescription ){ this.columnDescription = columnDescription; }
   public void setIsMandatory( String columnMandatory ){ this.columnMandatory = "yes".equals( columnMandatory ); }
   public void setIsValueMandatory( String columnValueMandatory ){ this.columnValueMandatory = "yes".equals( columnValueMandatory ); }
   // @formatter:on

   // protected, private helper methods
   private void combineReturnMessage( String additionalMessage ) {
      if( additionalMessage.equals( PASS_TOKEN ) ){
         if( this.returnMessage == null || this.returnMessage.isEmpty() ){
            this.returnMessage = PASS_TOKEN;
         }
      }else if( this.returnMessage != null && this.returnMessage.equals( PASS_TOKEN ) ){
         this.returnMessage = additionalMessage;
      }else if( this.returnMessage != null ){
         this.returnMessage += "; " + additionalMessage;
      }else{
         this.returnMessage = additionalMessage;
      }
   }

   private void determineCurrentColumn() {
      Row headerRow = excelSheet.getRow( 0 );
      int columnIndex = 0;

      for( Cell cell : headerRow ){
         if( cell.getStringCellValue().equals( columnName ) ){
            this.columnIndex = columnIndex;
            break;
         }
         columnIndex++;
      }
   }

   private static String determineFilePart( String resourcePath ) {
      return StringUtils.substringBeforeLast( resourcePath, ":" );
   }

   private static String determineSheetName( String resourcePath ) {
      return StringUtils.substringAfterLast( resourcePath, ":" );
   }

   private void verifyColumnName() {
      String result = PASS_TOKEN;
      if( this.columnMandatory ){
         if( this.columnIndex != null ){
            result = PASS_TOKEN;
         }else{
            result = "failed: column not defined";
         }
      }
      combineReturnMessage( result );
   }

   private void verifyColumnType() {
      String result = PASS_TOKEN;
      for( Row row : excelSheet ){
         if( row.getRowNum() > 0 ){
            Cell cell = row.getCell( columnIndex );
            if( this.columnValueMandatory != null && this.columnValueMandatory && cell != null ){
               ExcelCellVerifier cellVerifier = new ExcelCellVerifier( cell );
               if( !cellVerifier.isTypeOf( this.columnType ) ){
                  result = "failed: data type mismatch";
                  break;
               }
            }
         }
      }
      combineReturnMessage( result );
   }

   private void verifyMandatoryColumn() {
      String result;
      if( (this.columnMandatory && columnIndex != null) || !this.columnMandatory ){
         result = PASS_TOKEN;
      }else{
         result = "failed: column is mandatory";
      }
      combineReturnMessage( result );
   }

   private void verifyMandatoryColumnValue() {
      String result = PASS_TOKEN;
      if( this.columnValueMandatory ){
         for( Row row : excelSheet ){
            if( row.getRowNum() > 0 ){
               ExcelCellVerifier cellVerifier = new ExcelCellVerifier( row.getCell( columnIndex ) );
               if( !cellVerifier.hasValue() ){
                  result = "failed: column value is mandatory";
                  break;
               }
            }
         }
      }

      combineReturnMessage( result );
   }
}
