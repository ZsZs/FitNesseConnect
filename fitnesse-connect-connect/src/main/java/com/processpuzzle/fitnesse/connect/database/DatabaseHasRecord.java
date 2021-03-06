package com.processpuzzle.fitnesse.connect.database;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class DatabaseHasRecord extends DatabaseFixture<SelectStatementBuilder> {
   private static Logger logger = LoggerFactory.getLogger( DatabaseHasRecord.class );
   private CellValueMapper cellValueMapper = new CellValueMapper();
   private List<String> columnNames = Lists.newArrayList();
   private String query;

   // constructors
   public DatabaseHasRecord( String configurationName, String queryString ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), queryString );
   }

   protected DatabaseHasRecord( ApplicationConfiguration serviceConfiguration, String queryString ) {
      this( serviceConfiguration, null, queryString );
   }

   protected DatabaseHasRecord( ApplicationConfiguration serviceConfiguration, String databaseName, String queryString ) {
      super( serviceConfiguration, databaseName );
      this.query = queryString;
      logger.info( "About to query database: " + databaseName + " with statement: " + queryString );
   }

   // public accessors and mutators
   public List<List<List<String>>> query() {
      List<List<List<String>>> rowList = Lists.newArrayList();
      List<Map<String, Object>> queryResult = databaseClient.query( query );

      for( Map<String, Object> map : queryResult ){
         List<List<String>> row = Lists.newArrayList();
         for( Entry<String, Object> mapEntry : map.entrySet() ){
            String value = cellValueMapper.map( mapEntry.getValue() );
            row.add( asList( mapEntry.getKey(), value ) );
         }
         rowList.add( row );
      }
      return rowList;
   }

   public void table( List<List<String>> table ) {
      for( List<String> row : table ){
         try{
            for( String cell : row ){
               columnNames.add( cell );
            }
         }catch( ClassCastException e ){
            logger.error( "Row cell value is not a String. See: " + row.toString() );
         }
      }
   }

   // properties
   // @formatter:off
   @Autowired public void setDatabaseClient( DatabaseConnector databaseClient ){ this.databaseClient = databaseClient; }
   public void setSql( String sql ) { this.query = sql; }
   // formatter:on

   private class CellValueMapper{
      public String map( Object cellValue ){
         String returnValue;
         if( cellValue instanceof Boolean ){
            returnValue = Boolean.toString( (boolean) cellValue );
         }
         else if( cellValue instanceof Integer ){
            returnValue = Integer.toString( (Integer) cellValue );
         }
         else if( cellValue instanceof Long ){
            returnValue = Long.toString( (Long) cellValue );
         }
         else if( cellValue instanceof Double ){
            returnValue = Double.toString( (Double) cellValue );
         }
         else if( cellValue instanceof BigDecimal ){
            returnValue = ((BigDecimal) cellValue ).toString();
         }
         else if( cellValue instanceof Timestamp ){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            returnValue = dateFormat.format( (Timestamp) cellValue );
         }
         else {
            returnValue = (String) cellValue;
         }
         return returnValue;
      }
   }
}
