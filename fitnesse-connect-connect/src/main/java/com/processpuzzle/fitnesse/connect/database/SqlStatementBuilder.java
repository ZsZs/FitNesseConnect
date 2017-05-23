package com.processpuzzle.fitnesse.connect.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public abstract class SqlStatementBuilder {
   private static final String GENERATED_FIELD_PREFIX = "Generated:";
   protected final String tableName;
   protected List<String> columnNames = Lists.newArrayList();
   protected List<String> headerRowReturn = Lists.newArrayList();
   protected String idColumn = null;
   protected List<List<String>> tableReturn = Lists.newArrayList();

   // constructors
   protected SqlStatementBuilder( String tableName ){
      this.tableName = tableName;
   }
   
   // public accessors and mutators
   public void analyseHeaderRow( List<String> headerRow ) {
      determineColumnNamesAndIdColumnsAndHeaderRowReturn( headerRow );
   }

   public Map<String, Object> buildColumnValueMap( List<String> valueRow ) {
      final Map<String, Object> parameters = new HashMap<>();
   
      for( int i = 0; i < columnNames.size(); i++ ){
         if( !isFitNesseSymbol( valueRow.get( i ) ) ){
            String columnName = columnNames.get( i );
            Object columnValue = valueRow.get( i );
            parameters.put( columnName, columnValue );
         }
      }
   
      return parameters;
   }

   public void buildValueRowReturn( List<String> valueRow, Long primaryKey ) {
      final List<String> valueRowReturn = Lists.newArrayList();
      String returnFlag = "";
   
      for( int i = 0; i < columnNames.size(); i++ ){
         if( isIdColumn( columnNames.get( i )) != null ){
            returnFlag = "pass:" + primaryKey.toString();
         }else{
            returnFlag = "pass";
         }
         valueRowReturn.add( returnFlag );
      }  
      
      tableReturn.add( valueRowReturn );
   }

   public Boolean hasIdColumn() {
      return idColumn != null;
   }

   public String getIdColumn() { return this.idColumn; }

   public String getTableName() { return this.tableName; }

   public List<List<String>> getTableReturn() { return this.tableReturn; }
   // @formatter:on

   protected String columnNameCSV() {
      String columnNameCSV = "";
      for( String columnName : columnNames ){
         columnNameCSV += columnName + ", ";
      }
   
      return StringUtils.substringBeforeLast( columnNameCSV, ", " );
   }

   protected String columnValueCSV( List<String> valueRow ) {
      List<String> valueRowReturn = Lists.newArrayList();
   
      String columnValueCSV = "";
      for( String columnValue : valueRow ){
         columnValueCSV += columnValue + ", ";
         valueRowReturn.add( "pass" );
      }
   
      tableReturn.add( valueRowReturn );
      return StringUtils.substringBeforeLast( columnValueCSV, ", " );
   }

   private void determineColumnNamesAndIdColumnsAndHeaderRowReturn( List<String> headerRow ) {
      for( String columnName : headerRow ){
         columnNames.add( columnName );
         if( isIdColumn( columnName ) != null){
            idColumn = isIdColumn( columnName );
         }
         headerRowReturn.add( "pass" );
      }
   
      tableReturn.add( headerRowReturn );
   }

   private String isIdColumn( String columnName ) {
      String isIdColumn = null;
      if( columnName.startsWith( GENERATED_FIELD_PREFIX ) ){
         isIdColumn = StringUtils.substringAfter( columnName, GENERATED_FIELD_PREFIX );
      }
      return isIdColumn;
   }

   private boolean isFitNesseSymbol( String columnValue ) {
      boolean isFitNesseSymbol = false;
      if( columnValue.startsWith( "$" ) && columnValue.endsWith( "=" ) ){
         isFitNesseSymbol = true;
      }
   
      return isFitNesseSymbol;
   }

}
