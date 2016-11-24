package com.processpuzzle.fitnesse.connect.database;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class DatabaseHasRecord extends DatabaseFixture{
   private CellValueMapper cellValueMapper = new CellValueMapper();
   private List<String> columnNames = Lists.newArrayList();
   private String query;

   // constructors
   public DatabaseHasRecord( String configurationName, String queryString ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), queryString );
   }
   
   DatabaseHasRecord( ApplicationConfiguration serviceConfiguration, String queryString ) {
      this( serviceConfiguration, null, queryString );
   }
   
   DatabaseHasRecord( ApplicationConfiguration serviceConfiguration, String databaseName, String queryString ) {
      super( serviceConfiguration, databaseName );
      this.query = queryString;
   }

   // public accessors and mutators
   public List<List<List<String>>> query() {
      List<List<List<String>>> rowList = Lists.newArrayList();
      List<Map<String, Object>> queryResult = databaseClient.query( query );

      for( Map<String, Object> map : queryResult ){
         List<List<String>> row = Lists.newArrayList();
         for( Entry<String, Object> mapEntry : map.entrySet() ){
            String value = cellValueMapper.map( mapEntry.getValue() );
            row.add( asList( mapEntry.getKey(), value ));
         }
         rowList.add( row );
      }
      return rowList;
   }

   public void table( List<List<String>> table ) {
      for( List<String> row : table ){
         for( String cell : row ){
            columnNames.add( cell );
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
         else {
            returnValue = (String) cellValue;
         }
         return returnValue;
      }
   }
}
