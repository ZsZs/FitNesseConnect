package com.processpuzzle.fitnesse.connect.database;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class InsertRecordIntoDatabase extends DatabaseFixture {
   private static final String GENERATED_FIELD_PREFIX = "Generated:";
   private static final String INSERT_SQL_TEMPLATE = "insert into {0} ( {1} ) values( {2} );";
   protected final String tableName;
   protected List<String> columnNames = Lists.newArrayList();
   protected List<String> headerRowReturn = Lists.newArrayList();
   protected String idColumn = null;
   protected List<List<String>> tableReturn = Lists.newArrayList();
   protected String insertSql;

   // constructors
   public InsertRecordIntoDatabase( String configurationName, String tableName ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), tableName );
   }
   
   private InsertRecordIntoDatabase( ApplicationConfiguration applicationConfiguration, String tableName ) {
      this( applicationConfiguration, null, tableName );
   }

   private InsertRecordIntoDatabase( ApplicationConfiguration applicationConfiguration, String databaseName, String tableName ) {
      super( applicationConfiguration, databaseName );
      this.tableName = tableName;
   }

   // public accessors and mutators
   public List<List<String>> doTable( List<List<String>> table ) {
      determineColumnNamesAndIdColumnsAndHeaderRowReturn( table.get( 0 ) );

      for( int i = 1; i < table.size(); i++ ){
         List<String> valueRow = table.get( i );
         Long primaryKey = null;

         if( idColumn == null ){
            buildInsertSql( valueRow );
            databaseClient.insert( insertSql );
         }else{
            Map<String, Object> columnValueMap = buildColumnValueMap( valueRow );
            SimpleJdbcInsert insert = new SimpleJdbcInsert( databaseClient.getJdbcTemplate() );
            insert.withTableName( this.tableName ).usingGeneratedKeyColumns( idColumn );
            final Number key = insert.executeAndReturnKey( columnValueMap );
            primaryKey = key.longValue();
         }

         List<String> valueRowReturn = buildValueRowReturn( valueRow, primaryKey );
         tableReturn.add( valueRowReturn );
      }

      return tableReturn;
   }

   private List<String> buildValueRowReturn( List<String> valueRow, Long primaryKey ) {
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
      
      return valueRowReturn;
   }

   // protected, private helper methods
   private Map<String, Object> buildColumnValueMap( List<String> valueRow ) {
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

   private void buildInsertSql( List<String> valueRow ) {
      insertSql = MessageFormat.format( INSERT_SQL_TEMPLATE, new Object[] { tableName, columnNameCSV(), columnValueCSV( valueRow ) } );
   }

   private String columnNameCSV() {
      String columnNameCSV = "";
      for( String columnName : columnNames ){
         columnNameCSV += columnName + ", ";
      }

      return StringUtils.substringBeforeLast( columnNameCSV, ", " );
   }

   private String columnValueCSV( List<String> valueRow ) {
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
